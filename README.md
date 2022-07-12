* # Gathering

  > Flow Week1 4분반 팀 김태훈, 김사은

  * 모임을 만들어주는 Android 기반 어플리케이션입니다.  
  * 모임을 만드려고 할 수도, 이미 만들어진 모임에 들어갈 수도 있습니다.  
  * 모임에 들어가는데 전혀 제약이 없고 매력 지수 등으로 같은 모임원을 평가할 수 있습니다.  
  * 매력 지수에 따라 칭호가 변할 수도 있습니다.  

    ![KakaoTalk_20220712_204150879](https://user-images.githubusercontent.com/48674812/178486156-e0acd346-d16c-4415-8777-b2114420555f.png)

  ***

  ### A. 개발 팀원  

  * 한양대 컴퓨터소프트웨어학부 [김태훈](https://github.com/twodf78)  
  * 숙명여대  컴퓨터 과학과 [김사은](https://github.com/valuec)  

  ***

  ### B. 개발 환경  

  * OS: Android 

  ```
  minSdk 21
  targetSdk 32
  ```

  * Language: JAVA  

  * IDE: Android Studio  

  * Target Device: Galaxy S7  

  ***

  ### C. 어플리케이션 소개  

  ### TAB 1 - Chatting (Party)

  ![KakaoTalk_20220712_204150879_12](https://user-images.githubusercontent.com/48674812/178486593-301d6a91-e8d2-4e0d-ace7-ce2c960082d0.png)

  ![KakaoTalk_20220712_204150879_13](https://user-images.githubusercontent.com/48674812/178486664-3e80b30b-52d0-4804-9962-3be15f99b651.png)

  
  
  
  
  #### Major features   
  
  * 이미 만들어진 자신의 채팅방(party)을 볼 수 있습니다.
    * 들어가면 채팅을 할 수 있습니다. 
  * 눌러서 들어가면 기존의 채팅 내역들이 나와있습니다.
  
  ***
  
  #### 기술 설명  
  
  * Retrofit 으로 요청을 보내서 Node.js에서 요청을 처리해주고, MySql DB로부터 데이터를 얻어옵니다.
  * 채팅방 목록은 본인이 가지는 party DB에서 갖고 있는 suggest_id들과 매칭하는 suggest DB로부터 가져온다.
  * 채팅방을 보여주는 것은 동기/비동기 요청 둘 다 사용해서 가져왔습니다.
  * 같은 activity 내에서 request에 대한 response를 그 뒤의 request가 써야하기 때문입니다.
  
  ***
  
  #### 주요기술: 
  
  
  * 동기 retrofit Request:
  
      * .clone()을 써서 새로운 thread로 돌려서 main thread의 부하를 낮춰야합니다.
      * 요청을 보낼때 type을 어떻게 받아오는 지 알고 있어야 합니다.
      * .execute()함수를 써서 retrofit request를 동기처리 할 수 있는데, 동기처리 된 request는 main thread가 결과가 오기 까지 기다립니다.
      
      ```java
      private void requestChatting(String suggest_id) {
          Call<List<PostChatting>> call_get = RetrofitClient.getAPIService().getChatting(suggest_id);
          try {
              List<PostChatting> result = call_get.clone().execute().body();
              for(int i = 0; i<result.size();i++){
                  adapter.setMessageList(result.get(i).getMessage());
                  userList.add(result.get(i).getUser_id());
              }
          }catch (IOException e){
              e.printStackTrace();
          }
      }
      ```
  
  
  * 비동기 retrofit request:
  
      * 다른 thread로 실행시켜서 retrofit request가 올 때까지 기다리지 않습니다. 병렬적으로 처리를 하기 때문입니다. 효율적이라는 장점을 동반하는 동시에, tradeoff로 순서를 보장해줄 수 없다는 점이있습니다.
  
  
    ```java
        private void requestName(String user_id) {
    
            Call<ResponseBody> call_get = service.getUser(user_id);
            call_get.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            String result = response.body().string();
                            Log.v(TAG, "result = " + result);
                            try {
                                JSONArray userArr = new JSONArray(result);
                                adapter.setUserList(userArr.getJSONObject(0));
                                if(adapter.getUserItemCount() ==userList.size()){
                                    recyclerView.setAdapter(adapter);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.v(TAG, "error = " + String.valueOf(response.code()));
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.v(TAG, "Fail");
                    Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
                }
            });
        }
    ```
  

***

  ### TAB 2 - Suggest(제안서)

![KakaoTalk_20220712_204150879_06](https://user-images.githubusercontent.com/48674812/178486969-5cc8c537-9d7d-4256-b5ce-6ab7a88916f1.png)

  #### Major features   

  * 사람들이 올린 제안서를 볼 수 있습니다.
  * 이 제안서들을 클릭하면 상세 설명을 볼 수 있고, 인원 수가 다 차지 않는 한, 참여를 할 수 있습니다.
  * 인원 수가 다 찼거나, 자신이 올린 제안서이거나, 이미 참가한 party이거나 등의 경우에 대해선 참여를 할 수 없도록 예외처리를 하였습니다.
  * 제안서를 쓸 수 있는데, 제안서를 작성 완료하면, 곧바로 DB로 반영이 되어서, home 화면에서 본인이 올린 제안서를 볼 수 있습니다.

***

  #### 기술 설명  

  * Recyclerview로 adapter에 담은 DB 데이터들을 수직적으로 보여줍니다.
  * 그 중 하나의 제안서를 클릭하면 자세한 정보를 볼 수 있습니다.
  * 참여하기를 누를 시 현재인원이 올라가고, 수용인원과 현재인원이 같아질 시 더 이상 참여를 할 수 없게 됩니다.
  * 좌측 상단에는 제안서의 작성자의 정보를 볼 수 있고,  칭호, 매력지수 등을 직관적으로 볼 수 있습니다.

***

  #### 주요기술: CRUD


  * CRUD 기술 구현
    * Create, Post:
        * Post는 전달할 데이터의 형식이 class형식으로 전달됩니다. 
            * API: 전달하는 데이터 형식을 직접 구현합니다. 각 Post할 테이블마다 테이블의 열과 상응하는 변수들을 Class 안에 가지고 있습니다.
            * 서버: Body를 받고 Body를 데이터랑 각 column을 갖는 형식의 이름으로 가지고 있습니다
            * DB: 동일하게 Query를 처리합니다.

    * Read, Get :
        * Get을 통해 SELECT Query문을 호출합니다. 
          * API: url를 구성하고, 인자를 query형식으로 전달합니다.
          * 서버: 서버로부터 받은 Query를 mySQL서버로 전달하고 마찬가지로 정보를 서버로 전달합니다. 
          * DB: router, controller, service, query 등을 거쳐 DB까지 요청을 전달하고, 다시 클라이언트 측으로 정보를 전달합니다.
    * Update, Put:
        * Put은 Post와 흡사하게 Body를 인자로 넘겨주지만 추가적으로 Path도 필요합니다. 
          * API: Path는 Id가 필요한데 Url에도 동일하게 해당 Id를 넣어줍니다. Url에서 해당 Id를 중괄호로 묶어줍니다.
          * DB: Update query문을 받고 명령을 처리해줍니다.
          * 서버: Get과 Post방식과 다르게 Path를 받는 것이 아니라 Params형식으로 Id를 받습니다. Body를 받는 방식은 Post와 동일합니다.
    * Delete, Delete: Get과 흡사합니다.


* ```java
  @GET("/get/user")
  Call<ResponseBody> getUser(@Query("data") String data);
  
  @GET("/get/suggest")
  Call<ResponseBody> getSuggest();
  
  //:id로 찍으면 그냥 인자로 인식해버림
  @GET("/get/suggest/id")
  Call<ResponseBody> getSuggestById(@Query("data") String data);
  
  @GET("/get/suggest/created_by")
  Call<ResponseBody> getSuggestByUserId(@Query("data") String data);
  
  @GET("/get/party")
  Call<ResponseBody> getParty(@Query("data") String data);
  
  @GET("/get/hobby")
  Call<ResponseBody> getHobby(@Query("data") String data);
  
  @GET("/get/friend")
  Call<ResponseBody> getFriend(@Query("data") String data);
  
  @GET("/get/title")
  Call<ResponseBody> getTitle(@Query("data") String data);
  
  @GET("/get/chatting")
  Call<List<PostChatting>> getChatting(@Query("data") String data);
  
  
  
  @POST("/post/user")
  Call<PostUser> postUser(@Body PostUser post);
  
  @POST("/post/suggest")
  Call<PostSuggest> postSuggest(@Body PostSuggest post);
  
  @POST("/post/party")
  Call<PostParty> postParty(@Body PostParty post);
  
  @POST("/post/chatting")
  Call<PostChatting> postChatting(@Body PostChatting post);
  
  
  @PUT("/put/user/{id}")
  Call<PostUser> putUser(@Path("id") String id, @Body PostUser post);
  
  @PUT("/put/suggest/{id}")
  Call<PostSuggest> putSuggest(@Path("id") String id, @Body PostSuggest post);
  
  @PUT("/put/party/{user_id}")
  Call<PostParty> putParty(@Path("user_id") String user_id, @Body PostParty post);
  
  
  @DELETE("/delete/user")
  Call<Void> deleteUser(@Path("id") String id);
  
  @DELETE("/delete/suggest")
  Call<Void> deleteSuggest(@Path("id") String id);
  
  @DELETE("/delete/party")
  Call<Void> deleteParty(@Path("id") String id);
  ```





***

  ### TAB 3 - Mypage

![KakaoTalk_20220712_204150879_08](https://user-images.githubusercontent.com/48674812/178487128-f3cf37e6-adf4-4040-9e1e-945b477486e7.png)

  #### Major features   

  * 나의 정보, 이름, 매력지수 등등을 볼 수 있습니다.
  * 내가 쓴 제안서도 볼 수 있습니다.
  * 나의 팀원들을 평가할 수 있습니다.

***

  #### 기술 설명  

  * Oncreate특징을 이용해서 들어갈 때마다 새롭게 바뀐 매력지수를 업데이트합니다.
  * Fragment에서 Activity로 화면전환을 활용해 매력점수를 보내는 페이지를 구현했습니다.
  * Put을 적극적으로 이용했습니다.
