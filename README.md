# Connect-to-MySQL-Database-Android
Connect and fetch data from MySQL database in Android

Usage:

import following files in your activity

DBConnection;
JSONListener;

Add this code in your activity

      public void fetchData() {
              try {
                      /****** If json returns Array ******/			
                      userId = 4; //*userId*/
                      json.put("user_id", userId);
                      url = "getuserDetailsById.json";
                      new DBConnection(this).execute(url, json.toString());

              } catch (JSONException e) {
                // TODO Auto-generated catch block
                      e.printStackTrace();

              }
      }
      
      @Override   //*get data returned from DB here*//
      public void JSONFeedBack(String result) { 
        // TODO Auto-generated method stub
             String userDetails = new String(result);
      }

Call fetchData() function where you want to use the data.
Always check for connection availability before connecting to DB. 
      
