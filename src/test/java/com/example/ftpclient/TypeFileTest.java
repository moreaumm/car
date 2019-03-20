package com.example.ftpclient;

import org.junit.Test;

import static org.junit.Assert.*;

public class TypeFileTest {


       @Test
        public void TestTypeASCII(){
            assertEquals("A",TypeFile.ASCII.toString());
        }

        @Test
        public void TestTypeBINARY(){
            assertEquals("I",TypeFile.BINARY.toString());
        }



}