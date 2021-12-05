# IoTCloudPlatform
# AWS를 이용한 공공 쓰레기통 관리 서비스
### 공공 쓰레기통을 보다 효율적으로 관리하기 위해, 쓰레기통의 잔여량을 알려주고, 쓰레기통을 제어할 수 있는 IoT 서비스를 개발
1. Arduino MKR WiFi 1010에 연결된 LED의 현 상태와 초음파 센서를 이용해 쓰레기를 감지해 DynamoDB에 Upload한다.
2. Android App을 통해 실시간으로 쓰레기통 잔여량 정보를 조회할 수 있고, 쓰레기통 OPEN/CLOSE 여부를 조회 및 제어를 할 수 있다. (쓰레기통 개폐 여부는 LED로 표현)
3. DynamoDB에 저장된 쓰레기통 잔여량 로그 조회 그래프 기능을 이용하여 쓰레기통을 빨리 차는 시간대를 파악하여 효율적으로 공공 쓰레기통을 관리할 수 있도록 도와준다.
![구조도](https://user-images.githubusercontent.com/80217683/144735158-489b88dd-0487-4a5a-8ed4-3f8dfde1d8d7.JPG)
