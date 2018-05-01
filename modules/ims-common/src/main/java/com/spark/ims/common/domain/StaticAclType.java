package com.spark.ims.common.domain;

/**
 * Created by liyuan on 2018/4/27.
 */
public enum StaticAclType {
    AUTHENTICATED {
        @Override
        public String toString() {
            return "authenticated";
        }
    }, UNAUTHENTICATED {
        @Override
        public String toString() {
            return "unAuthenticated";
        }
    };

}
