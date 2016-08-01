package com.ancun.boss.business.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ancun.boss.constant.MessageConstant;
import com.ancun.core.exception.EduException;
/**
 *校检类
 */
public class AccountUtil {

	public static String SPLIT = ",";
	private static final int BUFFERSIZE = 8196;
	private static final String CHARSETNAME = "UTF-8";
	
    
    /**
     * 调用链接
     */
//    private static String url = "http://101.95.49.32/FPTC/Record"+ "?User="+ "haobai.ancun" + "&Token="+ "7b1d9d4fc96e12a263fc6541d0f2c763" + "&Action=start"+ "&";
    private static String url = PropertiesUtils.getConfig("HTTP_URL")+ "?User="+ PropertiesUtils.getConfig("HTTP_USER") + "&Token="+ PropertiesUtils.getConfig("HTTP_TOKEN") + "&Action=start&";
    
    private static final Logger loger = LoggerFactory.getLogger(AccountUtil.class);
	
    /**
     * 开通
     * @param userTel
     * @param type
     * @throws EduException
     */
    public static void accountOpen(String userTel,String type) throws EduException {
    	
    	url = url + type + "=" + userTel;

    	CloseableHttpClient client = HttpClients.createDefault();
		HttpGet method = new HttpGet(url);
		int status = 200;
		try {
			CloseableHttpResponse res = client.execute(method);		
			status = res.getStatusLine().getStatusCode();
			if(status==200){
				HttpEntity entity = res.getEntity();
				InputStream in = entity.getContent();
				String str = inputStreamToXml(in);
				loger.debug("上海远程调用url:{}", url);
				loger.debug("上海远程调用xml:{}", str);
				//没抛出错误就是正确
				Xml2MapUtil.convertToMap(str);
				in.close();
				EntityUtils.consume(entity);
			}else{
				loger.error("上海远程调用错误，status:{}，url:{}", status,url);
				throw new EduException(MessageConstant.SH_POST_ERROR_131019);
			}
		} catch (Exception e) {
			loger.error("上海远程调用异常，e:{}", e.getMessage());
			throw new EduException(MessageConstant.SH_POST_ERROR_131018);
		}finally{
			try {
	            client.close();
            } catch (IOException ioe) {
            	loger.error("上海远程调用异常,链接关闭异常，e:{}", ioe.getMessage());
            	ioe.printStackTrace();
            }
		}
		
    }

    /**
     * 注销
     * @param userTel
     * @param type
     * @throws EduException
     */
    public static void accountStop(String userTel,String type) throws EduException {
    	
    	url = url + type + "=" + userTel;
    	
    	CloseableHttpClient client = HttpClients.createDefault();
		HttpGet method = new HttpGet(url);
		int status = 200;
		try {
			CloseableHttpResponse res = client.execute(method);			
			status = res.getStatusLine().getStatusCode();
			if(status==200){
				HttpEntity entity = res.getEntity();
				InputStream in = entity.getContent();
				String str = inputStreamToXml(in);
				loger.debug("上海远程调用url:{}", url);
				loger.debug("上海远程调用xml:{}", str);
				//没抛出错误就是正确
				Xml2MapUtil.convertToMap(str);
				in.close();
				EntityUtils.consume(entity);
			}else{
				loger.error("上海远程调用错误，status:{}，url:{}", status,url);
				throw new EduException(MessageConstant.SH_POST_ERROR_131019);
			}
		} catch (Exception e) {
			loger.error("上海远程调用异常，e:{}", e.getMessage());
			throw new EduException(MessageConstant.SH_POST_ERROR_131018);
		}finally{
			try {
	            client.close();
            } catch (IOException ioe) {
            	loger.error("上海远程调用异常,链接关闭异常，e:{}", ioe.getMessage());
            	ioe.printStackTrace();
            }
		}
		
    }
    
    private static String inputStreamToXml(InputStream in) throws IOException{
		String  xmlString = null;
	    
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    byte[] data = new byte[BUFFERSIZE];
	    int len = 0;
		while((len=in.read(data))!=-1){
			outputStream.write(data, 0, len);
		}
		xmlString = new String(outputStream.toByteArray(), CHARSETNAME);
		
		in.close();
	    
	    return xmlString;
	}
    

    public static void main(String[] args) {
    	try {
			AccountUtil.accountOpen("02162108370","Caller");
		} catch (EduException e) {
			e.printStackTrace();
		}
//        try {
//               String endpoint = "http://101.95.49.32/FptcSrvcInfoSync/FptcSrvcInfoSyncSOAP?wsdl";
//               //直接引用远程的wsdl文件
//              //以下都是套路 
//               Service service = new Service();
//               Call call = (Call) service.createCall();
//               call.setTargetEndpointAddress(endpoint);
//               call.setOperationName("addUser");//WSDL里面描述的接口名称
//               call.addParameter("userName", org.apache.axis.encoding.XMLType.XSD_DATE,
//                             javax.xml.rpc.ParameterMode.IN);//接口的参数
//               call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型  
//               String temp = "测试人员";
//               String result = (String)call.invoke(new Object[]{temp});
//               //给方法传递参数，并且调用方法
//               System.out.println("result is "+result);
//        }
//        catch (Exception e) {
//               System.err.println(e.toString());
//        }
    }
}
