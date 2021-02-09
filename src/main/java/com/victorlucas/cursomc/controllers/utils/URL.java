package com.victorlucas.cursomc.controllers.utils;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


public class URL {

    public static String decodeParam(String s) {
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }

        /*
            Esse URLDecoder.decode ele serve para descodificar a string. Qualquer dúvida rever Seção 3 aula 51.
        */
    }

    public static List<Integer> decodeIntList(String s) {

        String[] vet = s.split(",");
 		List<Integer> list = new ArrayList<>();
 		for (int i=0; i<vet.length; i++) {
 			list.add(Integer.parseInt(vet[i]));
 		}
 		return list;
        /*
 		O código abaixo resume o tudo isso feito em cima em apenas uma linha.
        */
        //return Arrays.stream(s.split(",")).map(obj -> Integer.parseInt(obj)).collect(Collectors.toList());
    }
}
