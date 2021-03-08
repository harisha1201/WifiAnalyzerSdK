package com.act.sdk.wifianalyser.model.getSSID;

public class NW {


        private String Name;


        private int ErrorCode;

        private String Message;

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getName() {
            return this.Name;
        }


        public void setErrorCode(int ErrorCode) {
            this.ErrorCode = ErrorCode;
        }

        public int getErrorCode() {
            return this.ErrorCode;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }

        public String getMessage() {
            return this.Message;
        }
    }


