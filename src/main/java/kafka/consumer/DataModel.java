package kafka.consumer;

public class DataModel {
    private String Level;
    private String SonicTemprature;
    private String RelativeHumidty;
    private String Barometric;
    private String RecordNum;
    
    public DataModel(String RecordNum, String Level, String SonicTemprature, String RelativeHumidty, String Barometric) {
    	this.RecordNum=RecordNum;
    	this.Level = Level;
    	this.SonicTemprature = SonicTemprature;
    	this.RelativeHumidty = RelativeHumidty;
    	this.Barometric = Barometric;	
    }
    
    public DataModel() {
    	
    }
    
    public void setRecordNum(String RecordNum) {
    	this.RecordNum=RecordNum;
    }
    public String getRecordNum() {
    	return this.RecordNum;
    }
    
    public void setLevel(String Level) {
    	this.Level = Level;
    }
    
    public String getLevel() {
    	return this.Level;
    }
    
    public void setSonicTemprature(String SonicTemprature) {
    	this.SonicTemprature = SonicTemprature;
    }
    
    public String getSonicTemprature() {
    	return this.SonicTemprature;
    }
    
    public String getRelativeHumidty() {
    	return this.RelativeHumidty;
    }
    
    public void setRelativeHumidty(String RelativeHumidty) {
    	this.RelativeHumidty = RelativeHumidty;
    }
    
    public void setBarometric(String Barometric) {
    	this.Barometric = Barometric;
    }
    
    public String getBarometric() {
    	return this.Barometric;
    }
}

