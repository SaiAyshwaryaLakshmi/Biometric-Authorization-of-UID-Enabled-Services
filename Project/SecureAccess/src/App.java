import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import SecuGen.FDxSDKPro.jni.JSGFPLib;
import SecuGen.FDxSDKPro.jni.SGDeviceInfoParam;
import SecuGen.FDxSDKPro.jni.SGFDxDeviceName;
import SecuGen.FDxSDKPro.jni.SGFDxErrorCode;
import SecuGen.FDxSDKPro.jni.SGFDxSecurityLevel;
import SecuGen.FDxSDKPro.jni.SGFingerInfo;
import SecuGen.FDxSDKPro.jni.SGFingerPosition;
import SecuGen.FDxSDKPro.jni.SGImpressionType;
import SecuGen.FDxSDKPro.jni.SGPPPortAddr;

public class App {
	public static void showError(Long error) {
		switch (error.intValue()) {
		case 6:
			System.out.println("Error Loading Device Driver");
			break;
		case 0:
			System.out.println("Success..");
			break;
		}
	}

	public static void main(String[] a) {
		JSGFPLib jsgfpLib = new JSGFPLib();

		/** Detect the device */
		long error = jsgfpLib.Init(SGFDxDeviceName.SG_DEV_AUTO);
		showError(error);

		/** Open the device */
		error = jsgfpLib.OpenDevice(SGPPPortAddr.USB_AUTO_DETECT);
		showError(error);

		/** Get Image size of device */
		SGDeviceInfoParam deviceInfo = new SGDeviceInfoParam();
		error = jsgfpLib.GetDeviceInfo(deviceInfo);
		showError(error);

		if (error == SGFDxErrorCode.SGFDX_ERROR_NONE) {
			System.out.println("Image Width:" + deviceInfo.imageWidth);
			System.out.println("Image Height:" + deviceInfo.imageHeight);
		}

		/** Get Image One */
		BufferedImage img = new BufferedImage(deviceInfo.imageWidth,
				deviceInfo.imageHeight, BufferedImage.TYPE_BYTE_GRAY);

		byte[] capturedData = ((java.awt.image.DataBufferByte) img.getRaster()
				.getDataBuffer()).getData();
		long timeout = 10000;
		long quality = 50;

		error = jsgfpLib.GetImageEx(capturedData, timeout, 0, quality);

		int[] createdquality = new int[1];

		jsgfpLib.GetImageQuality(deviceInfo.imageWidth, deviceInfo.imageHeight,
				capturedData, createdquality);

		System.out.println("Created Image Quality:" + createdquality[0]);

		SGFingerInfo fingerInfo = new SGFingerInfo();
		fingerInfo.FingerNumber = SGFingerPosition.SG_FINGPOS_LI;
		fingerInfo.ImageQuality = createdquality[0];
		fingerInfo.ImpressionType = SGImpressionType.SG_IMPTYPE_LP;
		fingerInfo.ViewNumber = 1;
		showError(error);

		// Create image template to verify
		byte[] firstTemplate = new byte[400];
		jsgfpLib.CreateTemplate(fingerInfo, capturedData, firstTemplate);

		System.out.println("Image Size:" + capturedData.length);
		try {

			ImageIO.write(img, "jpg", new File("d:\\scanner11.jpg"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/** Get Image One */
		BufferedImage img2 = new BufferedImage(deviceInfo.imageWidth,
				deviceInfo.imageHeight, BufferedImage.TYPE_BYTE_GRAY);

		byte[] capturedData2 = ((java.awt.image.DataBufferByte) img2.getRaster()
				.getDataBuffer()).getData();

		error = jsgfpLib.GetImageEx(capturedData2, timeout, 0, quality);

		int[] createdquality2 = new int[1];

		jsgfpLib.GetImageQuality(deviceInfo.imageWidth, deviceInfo.imageHeight,
				capturedData2, createdquality2);

		System.out.println("Created Image Quality:" + createdquality2[0]);

		SGFingerInfo fingerInfo2 = new SGFingerInfo();
		fingerInfo2.FingerNumber = SGFingerPosition.SG_FINGPOS_LI;
		fingerInfo2.ImageQuality = createdquality[0];
		fingerInfo2.ImpressionType = SGImpressionType.SG_IMPTYPE_LP;
		fingerInfo2.ViewNumber = 1;
		showError(error);

		// Create image template to verify
		byte[] secondTemplate = new byte[400];
		jsgfpLib.CreateTemplate(fingerInfo2, capturedData2, secondTemplate);

		System.out.println("Image Size:" + capturedData2.length);
		try {

			ImageIO.write(img2, "jpg", new File("d:\\scanner2.jpg"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/** Get Image Three */
		BufferedImage img3 = new BufferedImage(deviceInfo.imageWidth,
				deviceInfo.imageHeight, BufferedImage.TYPE_BYTE_GRAY);

		byte[] capturedData3 = ((java.awt.image.DataBufferByte) img3.getRaster()
				.getDataBuffer()).getData();

		error = jsgfpLib.GetImageEx(capturedData3, timeout, 0, quality);

		int[] createdquality3 = new int[1];

		jsgfpLib.GetImageQuality(deviceInfo.imageWidth, deviceInfo.imageHeight,
				capturedData3, createdquality3);

		System.out.println("Created Image Quality:" + createdquality3[0]);

		SGFingerInfo fingerInfo3 = new SGFingerInfo();
		fingerInfo2.FingerNumber = SGFingerPosition.SG_FINGPOS_LI;
		fingerInfo2.ImageQuality = createdquality[0];
		fingerInfo2.ImpressionType = SGImpressionType.SG_IMPTYPE_LP;
		fingerInfo2.ViewNumber = 1;
		showError(error);

		// Create image template to verify
		byte[] verifyTemplate = new byte[400];
		jsgfpLib.CreateTemplate(fingerInfo3, capturedData3, verifyTemplate);

		System.out.println("Image Size:" + capturedData3.length);
		try {

			ImageIO.write(img3, "jpg", new File("d:\\scanner3.jpg"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/**Verification of fingure prints**/
		
		long secuLevel =SGFDxSecurityLevel.SL_NORMAL;
        boolean[] matched = new boolean[1];
        matched[0] = false;
        
        if(jsgfpLib.MatchTemplate(firstTemplate, verifyTemplate, secuLevel, matched)==0){
        	if(matched[0]){
        		System.out.println("Matched with First Fingure Print");
        	}else{
        		System.out.println("Not Matched with First Fingure Print");
        	}
        }
        
        if(jsgfpLib.MatchTemplate(secondTemplate, verifyTemplate, secuLevel, matched)==0){
        	if(matched[0]){
        		System.out.println("Matched with Second Fingure Print");
        	}else{
        		System.out.println("Not Matched with Second Fingure Print");
        	}
        }

		jsgfpLib.Close();
	}
}
