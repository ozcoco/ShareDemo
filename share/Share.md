# Share

------

## F0acebook Graph API

Facebook SDK

```groovy
implementation 'com.facebook.android:facebook-android-sdk:4.42.0'
```



### Auth2.0认证

#### 手动方式

重定向回调：

> ```
> https://ozcoco.github.io/facebookapi.html
> ```
>
> 

授权链接：

> ```
> https://www.facebook.com/v7.0/dialog/oauth?client_id=244461150108491&redirect_uri=https%3A%2F%2Fozcoco.github.io%2Ffacebookapi.html&auth_type=rerequest&scope=email
> ```
>
> 

获取Token

> ​        
>
> ```
> GET https://graph.facebook.com/v7.0/oauth/access_token?
>    client_id={app-id}
>    &redirect_uri={redirect-uri}
>    &client_secret={app-secret}
>    &code={code-parameter}
> ```

检查Token

> ```
> GET graph.facebook.com/debug_token?
>      input_token={token-to-inspect}
>      &access_token={app-token-or-admin-token}
> ```
>
> 

```shell
Request:
https://www.facebook.com/v7.0/dialog/oauth?
client_id=244461150108491
&redirect_uri=https://ozcoco.github.io/facebookapi.html
&auth_type=rerequest
&scope=email
&state=123abc


Response:
code: AQDLv8B-hL23gXFivGJkY0nPKWQ0co_YY3-ssYCrHoDSj3xePolB7PCDPLEkJXvjg8awz2vsMmpI34IwopMk-WYT6sS5BwJz21-wN6wtM_sy_eSNYA1524cm-5-0J1MHNNnbnxrcKQk0jFNwDjbJLZjhVxpXC6C0DQYGnMxNPT5Xv6_EjQo8rj6-9rZ-E53lXmbvd1B2uEgYwSoxRUqbYwd4SnJ21xYBn8X9DgUml4Es6cnvhTm2qOeIjCIavOh-DCKrZIp3tALk_IBo0khIVC4mHc8zdK_IQwzg5VJ_Y2v1Ky2AXEAM47pU4qWx_Bx6yPemCW3CS4_vZlYg7EUIpsYa_Uq7HRciBdEjn-i3Mbwg9Q
state: 123abc


获取Token:
https://graph.facebook.com/v7.0/oauth/access_token?
client_id=244461150108491
&redirect_uri=https://ozcoco.github.io/facebookapi.html
&client_secret=40249a7f1f5748e557d3f3e60b22a472
&code=AQDmYmxqM4A_JStWEmQRJDJbiVEu9upJbr6eXdGZAYZ48trxnCn2xFJZucNxcSo5snfpg3i1rxRCfs0Cwax7Tqns2Zj4GURak37GpHUQ4PwSjS9AcEIGNCIg94Mah711zFJABD6hXT8kRPln0jEdvjyjx4yBmCynvlq4NM2n4XDNSoR9idv568Jk5y69xo6N0bu-CEKqAi_L6xKVgiq88y4g20oBjru9_J33pswoQzsgBWKJQS1u4qeP82TGRL3bCAmjlnP-XnwdNqRFDo-eMoPZYN1iQ9DqS8Q2enN4jYhzd_44KcWDEHWCttCxlgU7wsnNJ4omWCZZW3JLEsAPuyP8f125LazOnfYASYhCUDW00A


Token:
{"access_token":"EAADeVgwDe0sBAPGwKHgj4VqraA1fybNoZAtwXwvZBEOorMAU07c90ddBf0gqIY0JL2O5KJRXka8tm6k1sZA1chmjCe2ZAAwWkkr3a84wXZCTW3HDngdNgHTTcVqFcxlAvwhfmQtdaJyZCt0E4hlaYWzH4ZCAWeNlmSPzGrZC07yOtV2ZAeIm3YgjaLnU42T7aflkgxn0kZCnwaOgZDZD","token_type":"bearer","expires_in":5181143,"auth_type":"rerequest"}

检查Token:
graph.facebook.com/debug_token?
input_token=EAADeVgwDe0sBAPGwKHgj4VqraA1fybNoZAtwXwvZBEOorMAU07c90ddBf0gqIY0JL2O5KJRXka8tm6k1sZA1chmjCe2ZAAwWkkr3a84wXZCTW3HDngdNgHTTcVqFcxlAvwhfmQtdaJyZCt0E4hlaYWzH4ZCAWeNlmSPzGrZC07yOtV2ZAeIm3YgjaLnU42T7aflkgxn0kZCnwaOgZDZD
&access_token=EAADeVgwDe0sBAPGwKHgj4VqraA1fybNoZAtwXwvZBEOorMAU07c90ddBf0gqIY0JL2O5KJRXka8tm6k1sZA1chmjCe2ZAAwWkkr3a84wXZCTW3HDngdNgHTTcVqFcxlAvwhfmQtdaJyZCt0E4hlaYWzH4ZCAWeNlmSPzGrZC07yOtV2ZAeIm3YgjaLnU42T7aflkgxn0kZCnwaOgZDZD

检查Token返回数据：
{"data":{"app_id":"244461150108491","type":"USER","application":"ShareDemo","data_access_expires_at":1597316802,"expires_at":1594721998,"is_valid":true,"issued_at":1589537998,"metadata":{"auth_type":"rerequest"},"scopes":["user_birthday","user_hometown","user_location","user_likes","user_photos","user_videos","user_friends","user_status","user_tagged_places","user_posts","user_gender","user_link","user_age_range","email","read_insights","publish_video","catalog_management","instagram_basic","instagram_manage_comments","instagram_manage_insights","leads_retrieval","whatsapp_business_management","public_profile"],"granular_scopes":[{"scope":"instagram_basic"},{"scope":"instagram_manage_comments"},{"scope":"instagram_manage_insights"},{"scope":"leads_retrieval"},{"scope":"whatsapp_business_management"}],"user_id":"854430338412041"}}
```



### Graph API

#### Share

##### photo

##### video