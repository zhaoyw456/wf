import java.util.*;
import java.util.Map.Entry;
import java.io.*;

/**
 * 
 * @ClassName: Test
 * @Description: TODO 词频统计
 * @author Administrator
 * @date 2020年9月19日
 *
 */

public class wf {
	public static int wordNum;
	public static int wordTop;
    public static void main(String[] args) throws IOException {
    	
    	if(args.length != 0) {
    		if(args[0].equals("-s")) {
    			if(args.length == 2) {
    				fileName(args[1]);
    			}else {
    				if(args.length == 4) {
    					wordNum = Integer.parseInt(args[2]);
    					wordTop	= Integer.parseInt(args[3]);
    					fileName(args[1]);
    				}else if(args.length == 3){
    					wordNum = Integer.parseInt(args[1]);
    					wordTop	= Integer.parseInt(args[2]);
    					redir();
    				}else {
    					redir();
    				}
    			}	
    		}else {
    			if(args.length == 3) {
    				wordNum = Integer.parseInt(args[1]);
					wordTop	= Integer.parseInt(args[2]);
    			}
    			fileName(args[0]);
    		}
    	}	
    }
    //文件名处理
    public static void fileName(String str) throws IOException  {
    	File file = new File(System.getProperty("user.dir")+"/"+str);
    	//功能三,处理目录
    	if(file.isDirectory()){
    		File[] tempList = file.listFiles();
    		for (int i = 0; i < tempList.length; i++) {
    			System.out.println(tempList[i].getName());
    			fileName(str+"/"+tempList[i].getName());
    			System.out.println("------------");
			}	
    	}else {
    		if(str.contains(".txt")) {
        		minFile(str);
        	}else {
        		englishFile(str);
        	}
    	}	
    }
    //功能一，小文件
    public static void minFile(String file) throws  IOException {
    	
    	Reader r = new FileReader(System.getProperty("user.dir")+"/"+file);
		BufferedReader br = new BufferedReader(r);
		wordHandle(br);
		br.close();
		r.close();
    }
    //功能二，支持命令行输入英文作品的文件名
    public static void englishFile(String file) throws  IOException {
    	Reader r = new FileReader(System.getProperty("user.dir")+"/"+file+".txt");
		BufferedReader br = new BufferedReader(r);
		wordHandle(br);
		br.close();
		r.close();
    }  
    //单词处理
	public static void wordHandle(BufferedReader br) throws IOException {
		Map<String,Integer> map = new HashMap<String,Integer>();
		String data = null;
		//读取文本一行
		while((data=br.readLine())!=null) {
			String[] word = data.split("[^a-zA-Z]"); 
			for (int i = 0; i < word.length; i++) {
				if(!word[i].equals("")) {       
					if(map.containsKey(word[i])) {
						Integer word_num = map.get(word[i]);
						map.put(word[i],++word_num);
					}else {
						map.put(word[i],1);
					}
				}	
			}
		}
		System.out.println("total  "+map.size());
		//利用集合的排序功能，比较map的value值，将10个最多的单词排在前面
		ArrayList<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Entry<String, Integer>>() {
            public int compare(Entry<String, Integer> o1,Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue(); 
            }
        });
        //输出前10个
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getKey() + " " + list.get(i).getValue());
            count++;
            if(count == 10) {
            	break;
            }
        }
        
        //功能五，输出单词字母数前top
        int count1 = 0;
        if(wordNum != 0 && wordTop != 0) {
        	System.out.println("-------"+"单词字母数"+wordNum+"前top"+wordTop);
            for (int i = 0; i < list.size(); i++) {
            	if(list.get(i).getKey().length() == wordNum) {
            		System.out.println(list.get(i).getKey() + " " + list.get(i).getValue());
            		count1++;
            	}   
                if(count1 == wordTop) {
                	break;
                }
            }
        }  
	}
	//功能四，重定向
	public static void redir() throws  IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		wordHandle(br);
		br.close();
    }  
}


