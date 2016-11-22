package com.imut.crawl.judge;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BloomFilterInit {

	private static BloomFilter bloomFilter = new BloomFilter();
	
	
	//getBloomFilterʵ�ֻ�ô����ϵĲ�¡������
	public static BloomFilter getBloomFilter() {
		
		try
		{
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("bf.bat"));
		
		BloomFilter bFilter = (BloomFilter)in.readObject();					
		bloomFilter = bFilter;
		in.close();
		}catch(Exception e){
			
			e.printStackTrace();
		}
			
			return bloomFilter;
		
	
	}
	
	//setBloomFilterʵ�ֲ�¡�������洢��������
	public void setBloomFilter(BloomFilter bf) {
		try{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("bf.bat"));
			out.writeObject(bloomFilter);
			out.close();
			
		}catch(Exception e){								
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		BloomFilter bloomFilter = new BloomFilter();
		try
		{
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("bf.bat"));
		BloomFilter bFilter = (BloomFilter)in.readObject();					
		bloomFilter = bFilter;
		in.close();
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		for(int i=0;i<3;i++){
			String linkHref = "http://www.hao123.com/";
			if (!bloomFilter.contains(linkHref)) {
				bloomFilter.add(linkHref);
				System.out.println("1111111111111111111111111111111111");
			} else {
				Thread.sleep(5000);
				System.out.println("�����ظ����ӣ�ֱ�Ӷ���");

			}
			try{
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("bf.bat"));
				System.out.println(bloomFilter);
				out.writeObject(bloomFilter);
				out.close();
				
			
			}catch(Exception e){								
				e.printStackTrace();
			}
		}
	}
	
}

