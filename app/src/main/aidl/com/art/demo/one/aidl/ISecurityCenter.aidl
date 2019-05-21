// ISecurityCenter.aidl
package com.art.demo.one.aidl;

// Declare any non-default types here with import statements

interface ISecurityCenter {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
void enctypt(String content);//加密
void decrypt(String password);//解密
}
