package com.centit.kubernetes.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("mysql实体类")
public class Mysql {
    
    public interface GetView {}
    
    @JsonView(GetView.class)
    @ApiModelProperty("实例名称")
    private String name;

    @JsonView(GetView.class)
    @ApiModelProperty("数据库端口")
    private int port;

    @ApiModelProperty("数据库初始密码")
    private String mysqlRootPassword;
    
    @JsonView(GetView.class)
    @ApiModelProperty("状态")
    private boolean ready;

    @JsonView(GetView.class)
    @ApiModelProperty("数据库版本")
    private String version;
    
    @ApiModelProperty(value = "是否区分大小写", notes="同mysql lower_case_table_names")
    private LowerCaseTableNames lowerCaseTableNames;

    @ApiModelProperty("服务器字符集")
    private String characterSetServer;

    @JsonView(GetView.class)
    @ApiModelProperty("CPU资源")
    private String cpu;

    @JsonView(GetView.class)
    @ApiModelProperty("内存资源")
    private String memory;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonView(GetView.class)
    @ApiModelProperty("创建时间")
    private Date gmtCreate;

    public Mysql() {
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean isReady) {
        this.ready = isReady;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    
    /**
     * https://dev.mysql.com/doc/refman/8.0/en/server-system-variables.html#sysvar_lower_case_table_names
     */
    public enum LowerCaseTableNames{
        /**
         * 区分大小写
         * @param lowerCaseTableNames
         */
        CASE_SENSITIVE(0),
        /**
         * 以小写存储，并且转换成小写进行比较
         */
        NOT_CASE_SENSITIVE(1),
        /**
         * 以原本形式存储，但是转换成小写进行比较
         */
        COMPARED_IN_LOWERCASE(2);

        private Integer value;

        private LowerCaseTableNames(Integer value){
            this.value = value;
        }

        public String toString() {
            return value.toString();
        }
    }

    public LowerCaseTableNames getLowerCaseTableNames() {
        return lowerCaseTableNames;
    }

    public void setLowerCaseTableNames(LowerCaseTableNames lowerCaseTableNames) {
        this.lowerCaseTableNames = lowerCaseTableNames;
    }

    public String getCharacterSetServer() {
        return characterSetServer;
    }

    public void setCharacterSetServer(String characterSetServer) {
        this.characterSetServer = characterSetServer;
    }


    public String getMysqlRootPassword() {
        return mysqlRootPassword;
    }

    public void setMysqlRootPassword(String mysqlRootPassword) {
        this.mysqlRootPassword = mysqlRootPassword;
    }

 

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getCpu() {
        return cpu;
    }

    public String getMemory() {
        return memory;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
