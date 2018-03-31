package rankhep.com.notthatway.model

import java.io.Serializable

/**
 * Created by choi on 2018. 3. 31..
 */
class User(var username: String,
           var id: String,
           var password: String,
           var usertype: Boolean,
           var guardiantoken: String = ""):Serializable {
}
//username : body.username,
//id : body.id,
//password : body.password,
//usertype: body.type,
//guardiantoken : body.guardiantoken