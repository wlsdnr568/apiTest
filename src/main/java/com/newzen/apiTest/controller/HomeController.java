package com.newzen.apiTest.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.newzen.apiTest.xml.Root;

@Controller
@RequestMapping(value = "/")
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "home";
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {
		return "test";
	}
	
	@RequestMapping(value = "/xml", method = RequestMethod.GET)
	public String xml() {
		return "xml";
	}
	
	@RequestMapping(value = "/getSbscrbSttusInfoSearch", method = RequestMethod.GET, produces = "application/text; charset=utf8")
	@ResponseBody 
	public String getSbscrbSttusInfoSearch(HttpServletResponse res) throws IOException {  
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552015/NpsSbscrbInfoProvdService/getSbscrbSttusInfoSearch"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=jfVZwgNtwemlwgn3NRJ01Hrcf4OMC7PUFRKBs4rioQiZ9l7GTjMlh8dZ3c5lbbcefI7A9P2NVmvWHezz4rp6xw%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ldong_addr_mgpl_dg_cd","UTF-8") + "=" + URLEncoder.encode("11", "UTF-8")); /*시도(행정자치부 법정동 주소코드 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("ldong_addr_mgpl_sggu_cd","UTF-8") + "=" + URLEncoder.encode("11110", "UTF-8")); /*시군구(행정자치부 법정동 주소코드 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("ldong_addr_mgpl_sggu_emd_cd","UTF-8") + "=" + URLEncoder.encode("11110101", "UTF-8")); /*읍면동(행정자치부 법정동 주소코드 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("jnngp_age","UTF-8") + "=" + URLEncoder.encode("28", "UTF-8")); /*가입자 연령*/
        urlBuilder.append("&" + URLEncoder.encode("sex_dvcd","UTF-8") + "=" + URLEncoder.encode("M", "UTF-8")); /*M:남자, F:여자*/
        urlBuilder.append("&" + URLEncoder.encode("jnng_brkd_jnngp_clss_cd","UTF-8") + "=" + URLEncoder.encode("0", "UTF-8")); /*0:사업장, 1:지역, 2:임의계속, 5:임의가입*/
        
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
		
		return sb.toString();
	}
	
	//사업장 정보 조회
	@RequestMapping(value = "/getDetailInfoSearch/{seq}", method = RequestMethod.GET, produces = "application/text; charset=utf8")
	@ResponseBody 
	public String getDetailInfoSearch(@PathVariable("seq")String seq) throws IOException, TransformerException {  
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552015/NpsBplcInfoInqireService/getDetailInfoSearch"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=jfVZwgNtwemlwgn3NRJ01Hrcf4OMC7PUFRKBs4rioQiZ9l7GTjMlh8dZ3c5lbbcefI7A9P2NVmvWHezz4rp6xw%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("seq","UTF-8") + "=" + URLEncoder.encode(seq, "UTF-8")); /*식별번호*/
        
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        
        //xml 정렬
        Source xmlInput = new StreamSource(new StringReader(sb.toString()));
        StringWriter stringWriter = new StringWriter();
        StreamResult xmlOutput = new StreamResult(stringWriter);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute("indent-number", 4);
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(xmlInput, xmlOutput);
        String outXml = xmlOutput.getWriter().toString();
        
		return outXml;
	}
	
	//사업장 식별번호 조회
	@RequestMapping(value = "/getBassInfoSearch/{compName}", method = RequestMethod.GET, produces = "application/text; charset=utf8")
	@ResponseBody 
	public String getBassInfoSearch(@PathVariable("compName") String compName) throws IOException, TransformerException {
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552015/NpsBplcInfoInqireService/getBassInfoSearch"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=jfVZwgNtwemlwgn3NRJ01Hrcf4OMC7PUFRKBs4rioQiZ9l7GTjMlh8dZ3c5lbbcefI7A9P2NVmvWHezz4rp6xw%3D%3D"); /*Service Key*/
//        urlBuilder.append("&" + URLEncoder.encode("ldong_addr_mgpl_dg_cd","UTF-8") + "=" + URLEncoder.encode("11", "UTF-8")); /*시도(행정자치부 법정동 주소코드 참조)*/
//        urlBuilder.append("&" + URLEncoder.encode("ldong_addr_mgpl_sggu_cd","UTF-8") + "=" + URLEncoder.encode("560", "UTF-8")); /*시군구(행정자치부 법정동 주소코드 참조)*/
//        urlBuilder.append("&" + URLEncoder.encode("ldong_addr_mgpl_sggu_emd_cd","UTF-8") + "=" + URLEncoder.encode("121", "UTF-8")); /*읍면동(행정자치부 법정동 주소코드 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("wkpl_nm","UTF-8") + "=" + URLEncoder.encode(compName, "UTF-8")); /*사업장명*/
//        urlBuilder.append("&" + URLEncoder.encode("bzowr_rgst_no","UTF-8") + "=" + URLEncoder.encode("105875", "UTF-8")); /*사업자등록번호(앞에서 6자리)*/
//        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*행갯수*/
        
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        
        //xml 정렬
        Source xmlInput = new StreamSource(new StringReader(sb.toString()));
        StringWriter stringWriter = new StringWriter();
        StreamResult xmlOutput = new StreamResult(stringWriter);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute("indent-number", 4);
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(xmlInput, xmlOutput);
        String outXml = xmlOutput.getWriter().toString();
        
        return outXml;
	}
	 
    @RequestMapping(value = "/service")
    @ResponseBody
    public Root test1(){
        
    	Root root;
        root = new Root("success!!");
 
        return root;
    }


}
