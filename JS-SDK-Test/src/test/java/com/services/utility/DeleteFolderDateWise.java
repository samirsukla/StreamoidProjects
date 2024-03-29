package com.services.utility;

import java.io.File;

import org.testng.annotations.Test;

public class DeleteFolderDateWise {
	
	static CreateAbsolutePath createabs;

	@Test
	public void deleteFiles(){
		createabs=new CreateAbsolutePath();
		String filePath = createabs.makeAbsolutePath();
		DeleteFolderDateWise del = new DeleteFolderDateWise();
		del.deleteFilesOlderThanNdays(31,filePath);
	}

	public void deleteFilesOlderThanNdays(final long daysBack, final String filePath) {

	   
		final File directory = new File(filePath);
	    if(directory.exists()){
	        System.out.println(" Directory Exists");
	        final File[] listFiles = directory.listFiles();          
	        final long purgeTime = System.currentTimeMillis() - (daysBack * 24 * 60 * 60 * 1000);
	        for(File listFile : listFiles) {
	            
	        	if(listFile.lastModified() < purgeTime) {
	                deleteFolder(listFile);

	            }
	        }
	    } 
	    else 
	    {
	    }
	}
	
	void deleteFolder(File file) {
	    File[] contents = file.listFiles();
	    if (contents != null) {
	        for (File f : contents) {
	            deleteFolder(f);
	        }
	    }
	    file.delete();
	}
}
