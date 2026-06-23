import os
import re

service_dir = 'src/main/java/com/hexaware/fastx/service'
test_dir = 'src/test/java/com/hexaware/fastx/service'

for file in os.listdir(service_dir):
    if file.endswith('Service.java') and not os.path.isdir(os.path.join(service_dir, file)):
        interface_path = os.path.join(service_dir, file)
        test_path = os.path.join(test_dir, file.replace('.java', 'Test.java'))
        
        with open(interface_path, 'r') as f:
            interface_content = f.read()
            
        # extract method names: type name(
        # public type name(
        service_methods = []
        for line in interface_content.split('\n'):
            line = line.strip()
            if line.endswith(';') and '(' in line:
                match = re.search(r'(\w+)\s*\(', line)
                if match:
                    service_methods.append(match.group(1))
        
        if os.path.exists(test_path):
            with open(test_path, 'r') as f:
                test_content = f.read()
                test_methods = re.findall(r'void\s+test(\w+)\(', test_content)
                test_methods_lower = [m.lower() for m in test_methods]
                
                print(f"--- {file} ---")
                for method in service_methods:
                    if method.lower() not in test_methods_lower and method not in ['List', 'String']:
                        print(f"Missing test for: {method}")
