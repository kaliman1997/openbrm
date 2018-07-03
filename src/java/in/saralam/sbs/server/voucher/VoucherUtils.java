/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package in.saralam.sbs.server.voucher;

import java.io.*;
import java.security.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author hmarikan
 */
public class VoucherUtils {

    /**
     * @param args the command line arguments
     */
        
   // Internal representation of voucher numbers is an array of bytes.  The
   // last 4 bytes of the voucher number contain a checksum that can be used
   // to check the previous 13 bytes for errors.
   private byte[] voucherNumber;
   public static final int VOUCHER_LENGTH  = 17;
   public static final int VOUCHER_CKS_POS = 13;
   public static final int VOUCHER_CKS_LEN = 4;
   
   
    
        public List<String> generateVoucherNumber(String prefix, int batchSize) throws Exception {
        
        List vouchers = new ArrayList<String>(); // = null;
        HashSet<String> randomParts =  getRandomNumber(batchSize);
        
       if(randomParts.size() != batchSize ) {
           
            throw new Exception("Invalid RandomPart Generation !");
       }
       
       //construct the voucher
       
       Iterator it = randomParts.iterator();
	   
	   System.out.println("iterator it :" + it);
       
       while(it.hasNext()) {
           StringBuilder buffer = new StringBuilder(VOUCHER_LENGTH );
           
           buffer.append(prefix);
           buffer.append(it.next());
           String checksum = makeChecksum(buffer.toString().substring(0, 13).getBytes("UTF-8"));
           buffer.append(checksum);
           String voucher = buffer.toString();
           
           if(verifyChecksum(voucher.getBytes("UTF-8"))) {
           } else {
              throw new Exception("Invalid voucher CheckSum!");
           }
           vouchers.add(voucher);
           
       }
   
                       
        return vouchers;
    }
    
   //--------------------------------------------------------------------------
   // Construct a VoucherNumber from a String by converting each character to
   // a single byte containing the UTF-8 encoded value of that character.
    
   public VoucherUtils(String vouch_str) throws Exception {
      voucherNumber = vouch_str.getBytes( "UTF-8" );
      if (voucherNumber.length != VOUCHER_LENGTH) {
         throw new Exception("Invalid voucher string!");
      }
      for (int i = 0; i < voucherNumber.length; i++) {
         if (voucherNumber[i] < (byte)('0') || voucherNumber[i] > (byte)('9')) {
            throw new Exception("Invalid voucher character!");
         }
      }
   }

   
   public VoucherUtils() throws Exception {
     
   }
   //--------------------------------------------------------------------------
   // Verify a voucher number's checksum property.  Return true if checksum is
   // correct, false otherwise.
   
   public boolean verifyChecksum(byte[] voucher) throws Exception {
     byte[] prefix = ("bunnyrabbit").getBytes("UTF-8");

     // Create a buffer big enough to hold the prefix and voucher data.
     byte[] data = new byte[prefix.length +
                            VOUCHER_LENGTH-VOUCHER_CKS_LEN];

     // Populate the data buffer from the prefix and voucher data.
     int idx = 0;
     while( idx < prefix.length) {
        data[idx] = prefix[idx];
        idx++;
     }
     for( int i = 0; i < VOUCHER_LENGTH-VOUCHER_CKS_LEN; i++) {
        data[idx++] = voucher[i];
     }

     // Get the MD5
     MessageDigest md = MessageDigest.getInstance("MD5");
     byte[] raw_digest = md.digest(data);

     // Use the first three bytes of the digest as an integer.
     int small_digest = (((int)(0xff&raw_digest[0])) << 16)
                      + (((int)(0xff&raw_digest[1])) << 8)
                      +  ((int)(0xff&raw_digest[2]));

     // Scale the value into the range [0000..9999].
     small_digest = (int)(((double)small_digest) / 1677.7216);

     // Convert to UTF-8 encoded bytes.
     byte[] checksum = String.format("%04d", small_digest).getBytes("UTF-8");

     // Return true if our computed checksum matches the one in voucherNumber.
     return Arrays.equals(checksum,
                          Arrays.copyOfRange(voucher,
                                           VOUCHER_CKS_POS,
                                           VOUCHER_CKS_POS+VOUCHER_CKS_LEN));
   }
   
   
     public HashSet<String>  getRandomNumber(int batchSize) {
        Random random = new Random();
        HashSet hs = new HashSet<String>();
        for (int i = 0 ; i < batchSize; i++) {
                String rand = String.format("%07d", random.nextInt(1000000));
                if( hs.contains(rand)) {
                        i--;
                        
                } else {
                        hs.add(rand);
                }
        }
        return hs;

    }
     
     
     
       
   public String makeChecksum(byte[] voucherNumber) throws Exception {
     byte[] prefix = ("bunnyrabbit").getBytes("UTF-8");

     // Create a buffer big enough to hold the prefix and voucher data.
     byte[] data = new byte[prefix.length +
                            VOUCHER_LENGTH-VOUCHER_CKS_LEN];

     // Populate the data buffer from the prefix and voucher data.
     int idx = 0;
     while( idx < prefix.length) {
        data[idx] = prefix[idx];
        idx++;
     }
     for( int i = 0; i < VOUCHER_LENGTH-VOUCHER_CKS_LEN; i++) {
        data[idx++] = voucherNumber[i];
     }

     // Get the MD5
     MessageDigest md = MessageDigest.getInstance("MD5");
     byte[] raw_digest = md.digest(data);

     // Use the first three bytes of the digest as an integer.
     int small_digest = (((int)(0xff&raw_digest[0])) << 16)
                      + (((int)(0xff&raw_digest[1])) << 8)
                      +  ((int)(0xff&raw_digest[2]));

     // Scale the value into the range [0000..9999].
     small_digest = (int)(((double)small_digest) / 1677.7216);
     String checksumString = String.format("%04d", small_digest);
     //System.out.println("Checksum String " + checksumString + " in " + small_digest);
     
     // Convert to UTF-8 encoded bytes.
     //byte[] checksum = String.format("%04d", small_digest).getBytes("UTF-8");
     
     return checksumString;
   }
  
}
