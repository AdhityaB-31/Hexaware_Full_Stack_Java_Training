package com.hexaware.fastx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.fastx.dto.BookingDto;
import com.hexaware.fastx.dto.PassengerDto;
import com.hexaware.fastx.dto.UserDto;
import com.hexaware.fastx.entity.Booking;
import com.hexaware.fastx.entity.Passenger;
import com.hexaware.fastx.entity.Role;
import com.hexaware.fastx.entity.User;
import com.hexaware.fastx.enums.Gender;
import com.hexaware.fastx.exception.ResourceNotFoundException;
import com.hexaware.fastx.exception.UserAlreadyExistsException;
import com.hexaware.fastx.repository.BookingRepository;
import com.hexaware.fastx.repository.RoleRepository;
import com.hexaware.fastx.repository.UserRepository;
import com.hexaware.fastx.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDto createUser(UserDto userDto) {
		if (userRepository.existsByEmail(userDto.getEmail())) {
			throw new UserAlreadyExistsException("User with email " + userDto.getEmail() + " already exists");
		}

		User user = mapToEntity(userDto);

		
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));

		User savedUser = userRepository.save(user);
		logger.info("User created with ID: {}", savedUser.getUserId());
		return mapToDto(savedUser);
	}

	@Override
	public UserDto updateUser(Long userId, UserDto userDto) {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

		user.setFullName(userDto.getFullName());
		user.setEmail(userDto.getEmail());

		// ✅ Hash new password before saving when profile is updated
		if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
			user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		}

		user.setPhoneNumber(userDto.getPhoneNumber());

		if (userDto.getGender() != null) {
			user.setGender(Gender.valueOf(userDto.getGender()));
		}

		user.setAddress(userDto.getAddress());
		user.setIsActive(userDto.getIsActive());

		if (userDto.getRoleId() != null) {
			Role role = roleRepository.findById(userDto.getRoleId())
					.orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + userDto.getRoleId()));
			user.setRole(role);
		}

		User updatedUser = userRepository.save(user);
		logger.info("User updated with ID: {}", updatedUser.getUserId());
		return mapToDto(updatedUser);
	}

	@Override
	public void deleteUser(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
		userRepository.delete(user);
		logger.info("User deleted with ID: {}", userId);
	}

	@Override
	public UserDto getUserById(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
		return mapToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDto> userDtos = new ArrayList<>();
		for (User user : users) {
			userDtos.add(mapToDto(user));
		}
		return userDtos;
	}

	@Override
	public List<BookingDto> getBookingHistory(Long userId) {
		userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

		List<Booking> bookings = bookingRepository.findBookingsByUserId(userId);
		List<BookingDto> bookingDtos = new ArrayList<>();
		for (Booking booking : bookings) {
			bookingDtos.add(mapBookingToDto(booking));
		}
		return bookingDtos;
	}

	@Override
	public UserDto getUserByEmail(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
		return mapToDto(user);
	}

	@Override
	public List<UserDto> searchUsersByName(String name) {
		List<User> users = userRepository.findByFullNameContainingIgnoreCase(name);
		List<UserDto> userDtos = new ArrayList<>();
		for (User user : users) {
			userDtos.add(mapToDto(user));
		}
		return userDtos;
	}

	@Override
	public List<UserDto> getActiveUsers() {
		List<User> users = userRepository.findByIsActiveTrue();
		List<UserDto> userDtos = new ArrayList<>();
		for (User user : users) {
			userDtos.add(mapToDto(user));
		}
		return userDtos;
	}

	private BookingDto mapBookingToDto(Booking booking) {
		BookingDto dto = new BookingDto();
		dto.setBookingId(booking.getBookingId());
		dto.setBookingDate(booking.getBookingDate());
		dto.setBookingStatus(booking.getBookingStatus() != null ? booking.getBookingStatus().name() : null);
		dto.setReservationExpiresAt(booking.getReservationExpiresAt());
		dto.setTotalAmount(booking.getTotalAmount());
		dto.setUserId(booking.getUser() != null ? booking.getUser().getUserId() : null);
		dto.setBusId(booking.getBus() != null ? booking.getBus().getBusId() : null);

		if (booking.getPassengers() != null) {
			List<PassengerDto> passengerDtos = new ArrayList<>();
			for (Passenger passenger : booking.getPassengers()) {
				PassengerDto passengerDto = new PassengerDto();
				passengerDto.setPassengerId(passenger.getPassengerId());
				passengerDto.setName(passenger.getName());
				passengerDto.setAge(passenger.getAge());
				passengerDto.setGender(passenger.getGender() != null ? passenger.getGender().name() : null);
				passengerDto.setSeatId(passenger.getSeat() != null ? passenger.getSeat().getSeatId() : null);
				passengerDto.setBookingId(booking.getBookingId());
				passengerDtos.add(passengerDto);
			}
			dto.setPassengers(passengerDtos);
		}
		return dto;
	}

	private UserDto mapToDto(User user) {
		UserDto dto = new UserDto();
		dto.setUserId(user.getUserId());
		dto.setFullName(user.getFullName());
		dto.setEmail(user.getEmail());
		dto.setPhoneNumber(user.getPhoneNumber());
		// Note: we do NOT return the hashed password in the response (security best practice)
		dto.setGender(user.getGender() != null ? user.getGender().name() : null);
		dto.setAddress(user.getAddress());
		dto.setIsActive(user.getIsActive());
		dto.setRoleId(user.getRole() != null ? user.getRole().getRoleId() : null);
		return dto;
	}

	private User mapToEntity(UserDto dto) {
		User user = new User();
		user.setFullName(dto.getFullName());
		user.setEmail(dto.getEmail());
		// password is set separately (after encoding) in createUser()
		user.setPhoneNumber(dto.getPhoneNumber());
		if (dto.getGender() != null) {
			user.setGender(Gender.valueOf(dto.getGender()));
		}
		user.setAddress(dto.getAddress());
		user.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);

		if (dto.getRoleId() != null) {
			Role role = roleRepository.findById(dto.getRoleId())
					.orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + dto.getRoleId()));
			user.setRole(role);
		}
		return user;
	}
}
