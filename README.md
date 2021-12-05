# IoTCloudPlatform
# AWS를 이용한 공공 쓰레기통 관리 서비스
### 공공 쓰레기통을 보다 효율적으로 관리하기 위해, 쓰레기통의 잔여량을 알려주고, 쓰레기통을 제어할 수 있는 IoT 서비스를 개발
쓰레기통 뚜껑에 부착된 초음파 센서를 이용해 쓰레기통의 잔여량을 측정하고, 쓰레기통 외부에 LED로 쓰레기통 이용 가능 여부를 알려줍니다.
1. Arduino MKR WiFi 1010에 연결된 LED의 현 상태와 초음파 센서를 이용해 쓰레기를 감지해 DynamoDB에 Upload합니다.
2. Android App을 통해 실시간으로 쓰레기통 잔여량 정보를 조회할 수 있고, 쓰레기통 OPEN/CLOSE 여부를 조회 및 제어를 할 수 있습니다. (쓰레기통 개폐 여부는 LED로 표현)
3. DynamoDB에 저장된 쓰레기통 잔여량 로그 조회 그래프 기능을 이용하여 쓰레기통을 빨리 차는 시간대를 파악하여 효율적으로 공공 쓰레기통을 관리할 수 있도록 도와줍니다.
![구조도](https://user-images.githubusercontent.com/80217683/144735158-489b88dd-0487-4a5a-8ed4-3f8dfde1d8d7.JPG)

* IoT 백엔드는 AWS의 AWS IoT Core, AWS Lambda, Amazon DynamoDB, Amazon API Gateway를 이용해 구축된 IoT 클라우드 플랫폼입니다.
  * AWS IoT Device gateway를 통해 연결된 MKRWiFi1010으로부터 주기적으로 매 1시간마다 쓰레기 잔여량 정보와 LED 상태를 수신하고, MQTT 프로토콜을 이용하여 Device shadow, IoT rule 컴포넌트와 상호작용합니다.
    * Device shadow는 Device gateway를 통해 게시된 주제에 따라 디바이스 상태정보를 업데이트하거나 현재 상태정보를 게시합니다.
    * IoT rule은 등록된 주제(update/documents)의 메시지가 수신될 때마다 AWS Lambda 함수를 통해서 수신된 메시지를 Amazon DynamoDB에 저장합니다.
  * Amazon API Gateway는 AWS Lambda함수를 통해서 아래 기능을 수행합니다.
    * AWS IoT Core에 등록된 디바이스 목록을 조회하는 REST API
    * 디바이스의 정보를 변경하거나 조회하는 REST API
    * 디바이스의 로그 정보를 조회하는 REST API

* 안드로이드 앱은 Amazon API Gateway를 통해서 게시된 REST API를 활용하여 IoT 백엔드와 상호작용하고, 아래와 같은 기능을 제공합니다.
  * 디바이스 목록 조회
  * 디바이스 상태 조회/변경
  * 디바이스 로그 조회

Arduino: https://github.com/jimin1211/IoTCloudPlatform/tree/main/AWS_IoT_DHT11-main/AWS_IoT_DHT11-main/AWS_IoT_DHT11

Android App: https://github.com/jimin1211/IoTCloudPlatform/tree/main/Android-RestAPI-master

DynamoDB 저장 Lambda: https://github.com/jimin1211/IoTCloudPlatform/tree/main/ProjectRecordingDeviceDataLambdaJavaProject

디바이스 목록 조회 Lambda: https://github.com/jimin1211/IoTCloudPlatform/tree/main/ProjectListingDeviceLambdaJavaProject

디바이스 상태 조회 Lambda: https://github.com/jimin1211/IoTCloudPlatform/tree/main/ProjectGetDeviceLambdaJavaProject

디바이스 상태 변경 Lambda: https://github.com/jimin1211/IoTCloudPlatform/tree/main/ProjectUpdateDeviceLambdaJavaProject

디바이스 로그 조회 Lambda: https://github.com/jimin1211/IoTCloudPlatform/tree/main/ProjectLogDeviceLambdaJavaProject
