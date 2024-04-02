package com.news.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author 归林
 * @date 2024/4/2
 */
public class SensitivewordUtil {
    public static Map sensitiveWordMap = new SensitiveWordInit().initKeyWord();
    public static int minMatchTYpe = 1;
    public static int maxMatchType = 2;

    public SensitivewordUtil(){
        sensitiveWordMap = new SensitiveWordInit().initKeyWord();
    }

    public static boolean isContaintSensitiveWord(String txt,int matchType){
        boolean flag = false;
        for(int i = 0 ; i < txt.length() ; i++){
            int matchFlag = CheckSensitiveWord(txt, i, matchType);
            if(matchFlag > 0){
                flag = true;
            }
        }
        return flag;
    }

    public static Set<String> getSensitiveWord(String txt , int matchType){
        Set<String> sensitiveWordList = new HashSet<String>();

        for(int i = 0 ; i < txt.length() ; i++){
            int length = CheckSensitiveWord(txt, i, matchType);
            if(length > 0){
                sensitiveWordList.add(txt.substring(i, i+length));
                i = i + length - 1;
            }
        }
        return sensitiveWordList;
    }

    public static String replaceSensitiveWord(String txt,int matchType,String replaceChar){
        String resultTxt = txt;
        Set<String> set = getSensitiveWord(txt, matchType);
        Iterator<String> iterator = set.iterator();
        String word = null;
        String replaceString = null;
        while (iterator.hasNext()) {
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }
        return resultTxt;
    }

    private static String getReplaceChars(String replaceChar,int length){
        String resultReplace = replaceChar;
        for(int i = 1 ; i < length ; i++){
            resultReplace += replaceChar;
        }
        return resultReplace;
    }

    public static int CheckSensitiveWord(String txt,int beginIndex,int matchType){
        boolean  flag = false;
        int matchFlag = 0;
        char word = 0;
        Map nowMap = sensitiveWordMap;
        for(int i = beginIndex; i < txt.length() ; i++){
            word = txt.charAt(i);
            nowMap = (Map) nowMap.get(word);
            if(nowMap != null){
                matchFlag++;
                if("1".equals(nowMap.get("isEnd"))){
                    flag = true;
                    if(SensitivewordUtil.minMatchTYpe == matchType){
                        break;
                    }
                }
            }
            else{
                break;
            }
        }
        if(matchFlag < 2 || !flag){
            matchFlag = 0;
        }
        return matchFlag;
    }
}
