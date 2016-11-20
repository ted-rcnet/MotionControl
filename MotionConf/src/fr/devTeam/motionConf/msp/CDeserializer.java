/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.msp;

/**
 *
 * @author EUGI7210
 */
public class CDeserializer {
    
    public static int deserialize8 (int data) {
        return deserialize8(data, true);
    }
    
    public static int deserialize8 (int data, boolean signed) {
        int deserialized;
        
        if (signed) {
            deserialized = ((data & 0x80) != 0) ? (data | 0xFFFFFF00) : data;
        } else {
            deserialized = data;
        }
        
        return deserialized;
    }
    
    public static int deserialize16 (int byte1, int byte2) {
        return deserialize16(byte1, byte2, true);
    }
    
    public static int deserialize16 (int byte1, int byte2, boolean signed) {
        int deserialized;
        
        if (signed) {
            deserialized = ((byte2 & 0x80) != 0) ?
                            byte1 | (byte2 << 8) | 0xFFFF0000 :
                            byte1 | (byte2 << 8);
        } else {
            deserialized = byte1 | (byte2 << 8);
        }
        
        
        return deserialized;
    }
    
    public static int deserialize16 (int[] byteArray, int startIndex) {
        return deserialize16(byteArray[startIndex], byteArray[startIndex+1]);
    }
    
    public static int deserialize16 (int[] byteArray, int startIndex, boolean signed) {
        return deserialize16(byteArray[startIndex], byteArray[startIndex+1], signed);
    }
    
    public static long deserialize32 (int byte1, int byte2, int byte3, int byte4) {
        return deserialize32(byte1, byte2, byte3, byte4, true);
    }
    
    public static long deserialize32 (int byte1, int byte2, int byte3, int byte4, boolean signed) {
        long deserialized = byte1 | (byte2 << 8) | (byte3 << 16) | (byte4 << 24);
        
        if (signed) {
            if ((byte4 & 0x80) != 0) deserialized |= 0xFFFFFFFF00000000l;
        }
        
        return deserialized;
    }
    
    public static long deserialize32 (int[] byteArray, int startIndex) {
        return deserialize32(byteArray[startIndex], byteArray[startIndex+1],
                            byteArray[startIndex+2],byteArray[startIndex+3]);
    }
    
    public static long deserialize32 (int[] byteArray, int startIndex, boolean signed) {
        return deserialize32(byteArray[startIndex], byteArray[startIndex+1],
                            byteArray[startIndex+2],byteArray[startIndex+3], signed);
    }
}
