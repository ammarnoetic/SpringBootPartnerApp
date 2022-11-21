package com.noetic.gwpartner.timwe.constants;



import com.noetic.gwpartner.timwe.Model.ConstantsAccessor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {


    public static final ConstantsAccessor constantsAccessor = new ConstantsAccessor("constants");

    public enum FileConstants {


        ENCODING,

        // The url at which SDP listens for MT request
        SDP_MT_GATEWAY_URL, // localhost:8080/dls_frontend/interface/mt_gateway

        // The service-specific account login info to authenticate at all MT
        // gateway services running for Fantasy football on SDP
        // passwords for authenticate MOs at this end is also the same
        MT_MO_SERVICE_USERNAME, MT_MO_SERVICE_PASSWORD,

        // The service ids for MT and MO service used for communicating with SDP
        MT_SERVICE_ID_FOR_TIMWE, MO_SERVICE_ID_FOR_TIMWE,

        // MT retry send settings
        MT_NO_OF_RETRIES, MT_RETRY_INTERVAL,

        // the listening parameters for MO requests from sdp
        MO_RECIEVE_CONTEXT, MO_RECIEVE_PORT,

        // in case of some exception, this response is sent
        FAILURE_RESPONSE,

        // shortcode eload.
        TIMWE_SHORTCODE,

        //Log properties file
        LOG4J_PROPERTIES_FILENAME,

        LOG4J_XML_FILENAME,

        LOG4J_USE_PROPERTIES_FILE,

        //DB properties file

        DB_PROPERTIES_FILE;


        public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
        private String text;

        private FileConstants()
        {
            this.text = constantsAccessor.getString(this.toString());
        }

        public String getString()
        {
            return text;
        }

        public Integer getInt()
        {
            try
            {
                return Integer.parseInt(this.text);
            }
            catch (NumberFormatException e)
            {
                return null;
            }
        }

        public Boolean getBoolean()
        {
            try
            {
                return Boolean.parseBoolean(this.text);
            }
            catch (NumberFormatException e)
            {
                return null;
            }
        }

        public Date getDate()
        {
            try
            {
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
                return sdf.parse(this.text);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }

        public static void reload()
        {
            for (FileConstants c : FileConstants.values())
            {
                c.text = constantsAccessor.getString(c.toString());
            }
        }
    }

    public static final String CURRENT_USER = "userAccountSession";
    public static final int ZERO = 0;
    public static final String COUNTRY_CODE = "92";
    public static final String ERROR_GENERIC = "Dear Retailer, we are unable to register your request, please check phone number and SIM number.";
    public static final String SUCCESS_MESSAGE = "Dear Retailer, your request is successfully registered!";

    public static final String TIMWE_KEYWORD_SHMO = "shmo";
    public static final String TIMWE_KEYWORD_MCN = "mcn";
    public static final String TIMWE_KEYWORD_YMN = "ymn";
    public static final String TIMWE_KEYWORD_GAME = "game";
    public static final String TIMWE_KEYWORD_SVPK = "svpk";


    public static final String phoneNumber1 = "923428503302";
    public static final String phoneNumber2 = "923458503304";

    public static int IS_POSTPAID = 103;
    public static String IS_POSTPAID_MSG = "Number is postpaid";
    public static int CHARGED_SUCCESSFUL = 0;
    public static String CHARGED_SUCCESSFUL_MSG = "Charged successful";
    public static int INSUFFICIENT_BALANCE = 124;
    public static String INSUFFICIENT_BALANCE_MSG = "Insufficient Balance";


//TimWe Constant URL
//	public static final String TIMWE_MO_URL = "http://xc.emea.timwe.com/webapp-mg-emea/mg/pknoeticworld/recmo";
//	public static final String Dot_URL = "http://10.13.32.156:10010/Air";
//	public static final String Warid_URL = "http://10.11.221.81:10010/Air";

//Centili Constant URL
    //public static final String TIMWE_MO_URL="http://api.centili.com/pp/common/noetic/premium/mosms";
    //public static final String Dot_URL = "http://10.13.32.156:10010/Air";
    //public static final String Warid_URL = "http://10.11.221.81:10010/Air";

    //Production
    //public static final String TIMWE_MO_URL = "http://18.136.252.66/app/dcb/noetic/mo_handler.php";
    //Staging
    public static final String PCOM_MO_URL = "http://www.sasha-lab.net/pakistan/noetic/mo";
    //public static final String JazzWarid_URL = "http://10.13.35.99:10010/Air";

    public static final String JazzWarid_URL = "http://10.13.32.179:10010/Air";
    public static final String Dot_URL = "http://10.13.32.179:10010/Air";

    public static final String Warid_URL = "http://10.11.221.81:10010/Air";
    public static final String SDPMTURL = "http://192.168.127.57:8081/sdp/interface/mt_gateway";


    //public static final String Dot_URL = "http://10.13.35.55:10010/Air";
    //	public static final String Dot_URL = "http://10.13.35.55:10010";
//	public static final String Dot_URL = "http://115.186.146.246/dot_billing/dot_test.php";
//	public static final String TIMWE_MO_URL = "http://195.23.53.124";
//	public static final String TIMWE_MO_URL = "http://127.0.0.1:10012/interface/moreceiver"; // For local Testing

    //public static final int SIM_NUMBER_LENGTH = 19;
    //public static final int  MSISDN_LENGTH =11;
    //public static final String SIM_NUMBER_INITIALS = "89410";
}
