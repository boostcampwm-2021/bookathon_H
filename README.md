# bookathon_H

## 서비스이름/설명

서비스명: **코비드**
- (구) 작업시간 알리미 / (현) **코**드 **비**서 해**드**립니다
- 개발자의 효율적인 작업 및 시간 관리를 도와주는 어플입니다.

```
개발자는 특정 작업을 할 때 얼마의 시간이 소요될지 과거의 경험으로 부터 알 수 있어야 한다...!
```



## 서비스핵심기능

- 스톱워치 Start, Pause, Resume, Reset, Notification 기능 제공
- GitHub 를 연동하여 Repository 목록 가져오기.
- 정해진 시간 내에 원하는 Repository 의 Task 를 작업하고, 작업한 시간을 누적 계산.
- 작업 시간에 대한 통계값을 Repository, Task 단위로 계산 및 UI에 표시.
- Repository, Task 목록 수정 (더하기, 삭제, 이름변경, ...) 기능

## 사용기술/라이브러리

- Android Framework
- Navigation
- RecyclerView

## 팀원소개

| <a href="https://github.com/kldaji"><img src="https://avatars.githubusercontent.com/u/78070388?v=4" width=100/><br><center>K008_김영욱</center></a> | <a href="https://github.com/uzucode"><img src="https://avatars.githubusercontent.com/u/86262602?v=4" width=100/><br> <center>K017_민호기</center></a> | <a href="https://github.com/svclaw2000"><img src="https://avatars.githubusercontent.com/u/46339857?v=4" width=100/><br><center>K018_박규훤</center></a> |
| ---------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------- |

K008_김영욱 : 열정! 열정! 열정! 만 있었다.

K017_민호기 : 내가 제일 꿀빨았다! (\^ㅅ\^)

K018_박규훤 : 그냥 쉴껄...

## 프로토타입

![프로토타입](https://i.imgur.com/amEW6u5.gif)

## 서비스동작영상(최대1분)

![](https://i.imgur.com/6C2TblE.gif)

## 각자개발담당부분구현정도와설명

### **K017_민호기 (타이머 fragment 담당)**
1. 타이머
    - android에서 제공하는 CountDownTimer를 상속받아 구현.

2. Circle 애니메이션
    - CircleView 커스텀뷰 제작.
    - canvas로 애니메이션 구현.

3. Notification 기능
    - timer (or stopwatch)가 expire되면 notification을 준다 (진동, 소리).

4. 선택된 task 누적시간 더하기
    - 이제 해야지... 하다가 못했다?!

5. 백그라운드에서도 타이머 돌게 하기.
    - 이틀안에 무리라구욧...!

</br>

### **K008_김영욱 (통계 fragment 담당)**

1. Project, Task RecylcerView
    - RecyclerView 의 itemType 을 Project 와 Task 로 구분.
    - Project 는 Task 의 List 를 갖고 있기 때문에 평탄화 시키는 작업 추가.
 
2. Project(parent), Task(child) 상관관계 구현
    - Project 체크박스가 체크되면 Task 들의 체크박스도 체크. (반대로도 적용)
    - Project 체크박스가 체크되었을 때 Task 체크박스를 해제하면 Project 체크박스가 해제되어야 하고, 반대로 모든 Task들의 체크박스가 체크되었을 때 Project 체크박스가 체크되어야 하지만 구현하지 못함.

3. Project 별 통계와 Task 별 통계 계산
    - Time 합계 및 평균값 계산 함수만 구현.

</br>

### **K018_박규훤 (설정 fragment 및 공통부 담당)**

1. 프로젝트 협업 시작 전 공통 코드 작성
    - ViewModel, Dto (Project, Task), Repository (SharedPreferences)

2. Github API를 사용해서 user ID를 검색하면 해당 user의 public repository목록 가져오기
    - AlertDialog와 RecyclerView를 사용하여 구현

3. Repository, Task 목록의 수정, 추가, 제거 기능 추가
    - RecyclerView와 ListAdapter를 사용하여 구현

4. 새벽 5시에 자는 투혼!!
5. 마지막까지 merge conflict 해결하느라 고생을 했다!!
