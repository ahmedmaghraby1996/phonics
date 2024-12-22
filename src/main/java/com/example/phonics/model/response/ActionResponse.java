package com.example.phonics.model.response;

import lombok.Data;

import java.util.HashMap;

@Data
public class ActionResponse<T> {


        private String message;

        private T data;

        public ActionResponse(T data) {
            this.message = "Success";
            this.data=data;
        }


}
