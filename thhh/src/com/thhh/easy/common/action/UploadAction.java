package com.thhh.easy.common.action;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.opensymphony.xwork2.ActionSupport;

public class UploadAction extends ActionSupport {

	//上传资料
	private File upFile;
	private String upFileFileName;
	private String upFileContentType;
//	private String brief;
	//设置上传路径
	String uploadPath = null;
	
//	private ITeacherUploadService teacherUploadService;
//	private Upload upload;
	
	@Override
	public String execute() throws Exception {
		uploadPath = "c:/upload";
		if(upFile != null){
			File saveFile = new File(new File(uploadPath), upFileFileName);
			if (!saveFile.getParentFile().exists())
				saveFile.getParentFile().mkdirs();
            FileUtils.copyFile(upFile, saveFile);
            
            saveFileMessage();
            
		}
		return "success";
	}

	public void saveFileMessage(){
//		Teacher teacher =(Teacher) ServletActionContext.getRequest().getSession()
//		.getAttribute("teacher");
//		
//		upload.setTeacher(teacher);
//		upload.setTime(new Date());
//		upload.setPath(uploadPath);
//		upload.setFileName(upFileFileName);
//		upload.setDownloadCount(0l);
//		
//		teacherUploadService.saveFile(upload);
	}
	
	
	
	public File getUpFile() {
		return upFile;
	}

	public void setUpFile(File upFile) {
		this.upFile = upFile;
	}

	public String getUpFileFileName() {
		return upFileFileName;
	}

	public void setUpFileFileName(String upFileFileName) {
		this.upFileFileName = upFileFileName;
	}

	public String getUpFileContentType() {
		return upFileContentType;
	}

	public void setUpFileContentType(String upFileContentType) {
		this.upFileContentType = upFileContentType;
	}

}
