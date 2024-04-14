# JSON(JavaScript Object Notation)이란?

### ref : [json.org](https://www.json.org)
---

### ■ JSON, XML등의 데이터 포맷을 사용하는 이유는 무엇일까?
  - 내가 만든 Class는 내가 만든 시스템 내에서 정의되고 사용된다.
  - 따라서 다른 외부 시스템에서는 내가 만든 Class를 이해하고 활용하기 어렵다.
  - 외부 시스템과 데이터 송/수신을 위해 상호작용을 할 때는 표준화된 형식으로 데이터를 표현해야 한다.
  - 이 때 표준화된 데이터 형식인 XML, JSON 포맷등을 사용해서 데이터를 전송하게 되는 것이다.
  - 표준화된 형식의 데이터를 수신받은 시스템에선 해당 데이터 포맷을 파싱하는 API등을 활용해서 데이터를 처리할 수 있게된다.
  

> 객체의 정보를 문자열의 형태로 표현하려고 할 때 어떤 방법이 효율적일까?
> 
  __1. 의미 없고 구분이 있는 문자열의 형태__
  ```java 
  String member = "홍길동,서울,010-1111-1111"+
                  "#나길동,대전,010-2222-2222"+
                  "#........";
  ```
   - '#' 을 구분자로 두어 단순하지만 데이터 처리에 어려움이 있다.
   - 또한 데이터간의 속성을 구별할 수 없다.
  
   __2. 의미가 있고 구분도 있는 문자열 형태(XML)__
  ```xml 
  <members>
    <member>
        <name>홍길동</name>
        <address>서울</address>
        <phone>010-1111-1111</phone>
    </member>
    <member>
        <name>나길동</name>
        <address>대전</address>
        <phone>010-2222-2222</phone>
    </member>
    .......
</members>
  ```
  - tag(<>)기반의 tree구조를 이용하여 데이터를 표현한다.
  - tag를 통해서 데이터와 데이터 속성 또한 구별할 수 있다.
  - 데이터의 표현이 자유롭고 처리가 쉬우나 그만큼 데이터의 크기가 커진다.
  
  __3. 의미가 있고 구분도 있는 문자열 형태(JSON)__
  ```json
 {
  "members": [
    {
      "name": "홍길동",
      "address": "서울",
      "phone": "010-1111-1111"
    },
    {
      "name": "나길동",
      "address": "대전",
      "phone": "010-2222-2222"
    },
    ..........
  ]
}
  ```
  - 데이터를 key-value의 구조로 나타내어 파싱이 간편하다.
  - xml에 비해 데이터 표현은 제한적이나 데이터의 크기가 작다는 장점이 있다.

### ■ JSON
- JSON은 데이터를 교환하기 위한 가장 많이 사용되고 있는 표준 형식 중 하나이다.
- JSON을 소개하는 [json.org](https://www.json.org)를 살펴보면 프로그래머에게 친숙한 규칙을 사용하고 __경량 데이터 교환 형식__ 이라고 JSON을 소개하고 있다.
- JSON은 가볍고 프로그래밍 언어와 상호 교환 가능한 형식의 구조를 가지고 있어 현재 WEB에서 가장 널리 사용되고 있다.
- 객체는 순서가 없고 key-value의 pair 구조를 { "key" : "value" } 로 표현한다.
- 배열은 순서(index)가 있고 [ {"key" : "value"}, {"key" : "value"} ] 형식으로 표현한다.
- key-value 구조에서 __value__ 는 문자열, 숫자, true, false, null, Object, Array등이 중첩될 수도 있다.

![value](https://github.com/Smart-Eddy/study_java_api/assets/112805025/14d5ec02-d806-4688-bd97-0956bc111a43)
<div style="text-align:center"><em>(JSON : value 구조)</em></div>
</br>

- JSON 포맷을 파싱하기 위해서 여러가지 API가 제공되고 활용할 수 있다
- Gson : Google에서 제공하는 JSON 라이브러리, 객체를 JSON 문자열로 파싱하거나 JSON 문자열을 객체로 역직렬화하는 작업을 수행하고 간단한 구문을 가지고 있어 사용하기 쉽다.
- Jackson : 빠르고 강력한 JSON 라이브러리, 데이터 바인딩, 트리 모델, 순수한 Streaming API를 지원한다.
- JSON In Java(org.json) : 주요 객체인 JSONObject와 JSONArray를 사용해서 JSON 데이터를 생성 및 파싱할 수 있는 라이브러리이다.
