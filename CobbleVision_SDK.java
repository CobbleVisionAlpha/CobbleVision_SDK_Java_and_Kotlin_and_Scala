// CobbleVision-API.java
// File contains code to communicate with the CobbleVision API

import java.util.concurrent.CompletableFuture
import org.bson.types.ObjectId 
import org.json.simple.JSONObject

// #################################################
// Preparation of Variables and Environment Settings.
// #################################################
public class GlobalVars {
  public Boolean environmentType = false
  public String serverAdress = "https://www.cobblevision.com"
  public Boolean debugging = true
}

public class CobbleVisionAPI{
  private String[] valid_price_categories = ["high", "medium", "low"]
  private String[] valid_job_types = ["QueuedJob"]
  
  if(GlobalVars.environmentType == false || Globalvars.environmentType === "demo"){
    private String BaseURL = "https://www.cobblevision.com"
  }else{
    private String BaseURL = GlobalVars.serverAdress + "/api/"
  }
  
  private String apiUserName = ""
  private String apiToken = ""
  
 // Function allows you to set the Username and Token for CobbleVision
 // @function setApiAuth()
 // @param {String} apiusername
 // @param {String} apitoken
 // @returns {Boolean} Indicating success of setting Api Auth.
  
  public Boolean setApiAuth(apiUserName, apiToken){
    this.apiUserName = apiUserName;
    this.apiToken = apiToken;
    return true;
  }
  
  # Function allows you to set the debugging variable
  # @function setDebugging()
  # @param {Boolean} debugBool
  # @returns {Boolean} Indicating success of setting Api Auth.
  
  public Boolean setDebugging(debugBool){
    this.debugging = debugBool;
    return true;
  }
  
  // #####################################################
  // # Functions for interacting with the CobbleVision API
  // #####################################################

  // # Return of the following functions is specified within this type description
  // # @typedef {Object} Entity Entity from HttpClient
  // # @method {InputStream} getContent() returns stream of Content

  // # This function uploads a media file to CobbleVision. You can find it after login in your media storage. Returns a response object with body, response and headers properties, deducted from npm request module
  // # @async
  // # @function uploadMediaFile()  
  // # @param {string} price_category - Either high, medium, low
  // # @param {boolean} publicBool - Make Media available publicly or not?
  // # @param {string} name - Name of Media (Non Unique)
  // # @param {array} tags - Tag Names for Media - Array of Strings
  // # @param {BufferedImage} file - BufferedImage - image to be uploaded
  // # @returns {Response} This return the UploadMediaResponse as HttpEntity. The body is in JSON format.

  @Async
  public CompletableFuture <HTTPEntity> uploadMediaFileAsync(String price_category, Boolean publicBool, String name, String[] tags, BufferedImage file) throws InterruptedException{
    try{
      private String endpoint = "media"
      
      if(this.BaseURL.charAt(this.BaseURL.length - 1) != "/"){
        throw new Exception("Cobble BasePath must end with a slash '/' ")
      }
      
      private String[] keyArray = ["price_category", "publicBool", "name", "tags", "Your Api User Key", "Your Api Token"]
      private Object[] valueArray = [pricate_category, publicBool, name, tags, apiUserName, apiToken]
      private String[] typeArray = ["String", "Boolean", "String", "Array", "String", "String"]
      
      try{
        checkTypeOfParameter(valueArray, typeArray)
      }catch e as Exception{
        private int err_message = parseInt(e.Message)
        if(err_message instanceof Integer){
          throw new Exception("The provided data is not valid: " + keyArray[err_message] + "is not of type " + "typeArray[err_message])
        }else{
          throw new Exception(e.printStackTrace)
        }
      }
      
      if(!check(this.valid_price_categories, price_category)){
        throw new Exception("Price Category is not valid!")
      }
      
      if (!(file instanceof BufferedImage)) throw new Error("File Object is not of type BufferedImage")
      
      private JSONObject obj = new JSONObject()
      obj.put("price_category", price_category)
      obj.put("public", publicBool)
      obj.put("name", name)
      obj.put("tags", tags)
      obj.put("file", new String(file.toString(), "ISO-8859-1"))
      
      private String jsonString = obj.toString()
     
      private ClosableHTTPClient client = HttpClients.createDefault();
      private HTTPPost HttpPost = new HttpPost(this.baseURL+endpoint)
      private StringEntity entity = new StringEntitty(jsonString)
      httpPost.setEntity(entity)
      httpPost.setHeader("Accept", "application/json")
      httpPost.setHeader("Content-Type", "application/json")
      private UsernamePasswordCredentials creds = new usernamePasswordCredentials(apiUserName, apiToken)
      httpPost.setHeader(new BasicScheme().authenticate(creds, httpPost, null))
      
      private ClosableHTTPResponse response = client.execute(httpPost)
      private HttpEntity postEntity = response.getEntity()
      client.close()
      
      if(GlobalVars.debugging) {
        System.out.println("Response from Upload Media Request = " + response.ToString())
      }
      
      return CompletableFuture.completeFuture(postEntity);
    }catch e as Exception{
    
      if(GlobalVars.debugging){
        System.out.println(e.printStackTrace)
      }
    
      throw new Exception(e.printStackTrace)
    }
  }
}
