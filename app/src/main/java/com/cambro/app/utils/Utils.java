package com.cambro.app.utils;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.cambro.app.PersonalizeToolActivity;
import com.cambro.app.interfce.Reg;
import com.cambro.app.interfce.SocialLoginService;
import com.cambro.app.model.FresFit;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.parse.ParseUser;

import twitter4j.auth.AccessToken;

public final class Utils {

	public static final boolean DEBUG = false;
	public static boolean signUp = false;
	public static boolean searchDone = false;
	public static boolean matchInfoFlag = false;
	public static ArrayList<Bitmap> matchTotalPhotos = new ArrayList<Bitmap>();
	public static ArrayList<ImageView> matchTotalImageViews = new ArrayList<ImageView>();
	public static ArrayList<HashMap<String, String>> UsersArrayList = new ArrayList<HashMap<String, String>>();
	public static int matchPersonID = 0;
	/*
	 * public static final String SignupURL =
	 * "http://anton.net16.net/dating/date.php"; public static final String
	 * SearchURL = "http://anton.net16.net/dating/search_user.php";
	 */

	public static int calculateInSampleSize(BitmapFactory.Options options,	int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.


			while ((halfHeight / inSampleSize) > reqHeight	&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;

			}
		}

		return inSampleSize;
	}

	public static void loadImageByName(String imageName, ImageView imageView)
	{
		File fileCapture = null;
		File f1 = new File(Environment.getExternalStorageDirectory().toString().trim());
		for (File temp : f1.listFiles()) {
			if (temp.getName().equals(imageName)) {
				fileCapture = temp;
				break;
			}
		}

		if(fileCapture.exists()){

			Bitmap myBitmap = BitmapFactory.decodeFile(fileCapture.getAbsolutePath());
			imageView.setImageBitmap(myBitmap);

		}
	}

	// getNode function
	public static String getNode(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();
		Node nValue = (Node) nlList.item(0);
		return nValue.getNodeValue();
	}

	public static Bitmap decodeSampledBitmapFromResource(Resources res,	int resId, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,	reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	public static int calculateQuestionNumber()
	{
		Calendar benginDay = Calendar.getInstance();
		benginDay.set(Calendar.DAY_OF_MONTH, 24);
		benginDay.set(Calendar.MONTH, 8);    //0~11 so 1 less
		benginDay.set(Calendar.YEAR, 2015);

		Calendar today = Calendar.getInstance();
		long diff = today.getTimeInMillis() - benginDay.getTimeInMillis();
		int weeks = (int)(diff / (24 * 60 * 60 * 7 * 1000));
//		Log.d("weeks", Integer.toString(weeks % 53));
		return (weeks + 34)  % 63;
	}

	public static HttpClient createHttpClient() {
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params,HTTP.DEFAULT_CONTENT_CHARSET);
		HttpProtocolParams.setUseExpectContinue(params, true);

		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schReg.register(new Scheme("https",	SSLSocketFactory.getSocketFactory(), 443));
		ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);

		return new DefaultHttpClient(conMgr, params);
	}

	public static final String TAG = "ToastExpander";

	public static void ToastshowFor(final Toast aToast,	final long durationInMilliseconds) {

		aToast.setDuration(Toast.LENGTH_LONG);

		Thread t = new Thread() {
			long timeElapsed = 0l;

			public void run() {
				try {
					while (timeElapsed <= durationInMilliseconds) {
						long start = System.currentTimeMillis();
						aToast.show();
						sleep(1750);
						timeElapsed += System.currentTimeMillis() - start;
					}
				} catch (InterruptedException e) {
//					Log.e(TAG, e.toString());
				}
			}
		};
		t.start();
	}

	public static void SelectSpinnerItemByValue(Spinner spnr, String value) {
		SimpleCursorAdapter adapter = (SimpleCursorAdapter) spnr.getAdapter();
		for (int position = 0; position < adapter.getCount(); position++) {
			if (adapter.getItem(position) == value) {
				spnr.setSelection(position);
				return;
			}
		}
	}

	public static int getIndex(Spinner spinner, String myString) {

		int index = 0;

		for (int i = 0; i < spinner.getCount(); i++) {
			if (spinner.getItemAtPosition(i).toString()
					.equalsIgnoreCase(myString)) {
				index = i;
				i = spinner.getCount();// will stop the loop, kind of break, by
										// making condition false
			}
		}
		return index;
	}	
	
	public static Bitmap downloadBitmap(String url) {
		// initilize the default HTTP client object
		final DefaultHttpClient client = new DefaultHttpClient();

		// forming a HttoGet request
		final HttpGet getRequest = new HttpGet(url);
		try {

			HttpResponse response = client.execute(getRequest);

			// check 200 OK for success
			final int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
//				Log.w("ImageDownloader", "Error " + statusCode
//						+ " while retrieving bitmap from " + url);
				return null;

			}

			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream inputStream = null;
				try {
					// getting contents from the stream
					inputStream = entity.getContent();

					// decoding stream data back into image Bitmap that
					// android understands
					final Bitmap bitmap = BitmapFactory
							.decodeStream(inputStream);

					return bitmap;
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		} catch (Exception e) {
			// You Could provide a more explicit error message for
			// IOException
			getRequest.abort();
		}

		return null;
	}
	
	public static String getPath(Uri uri, Activity activity) {
		String[] projection = { MediaColumns.DATA };
		Cursor cursor = activity
				.managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
	
	public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

	public static void loadFitImageByDrawableName(Context context, FresFit fit, ImageView imageView)
	{
		String imgName = "";
		try {
			imgName = "fitfactory_" + fit.getName().toLowerCase().trim().replace(" ", "_").replace("-","_");

			Drawable drawable = context.getResources().getDrawable(context.getResources()
					.getIdentifier(imgName, "drawable", context.getPackageName()));
			imageView.setImageDrawable(drawable);
		}
		catch (Exception e)
		{
//			Log.d("ImageName:", imgName);
		}

	}

	public static void loadFreshImageByDrawableName(Context context, ImageView imageView, String sDrawableName)
	{
		String[] ss= sDrawableName.split("-");
		String imgName = ss[ss.length -1].replace(".jpg","").replace(".png","");
		imgName = "fresh_fv_" + imgName.replace(" ", "_").replace("-","_");
		//imgName = "fresh_fv_cabbage";
		try {
			Drawable drawable = context.getResources().getDrawable(context.getResources()
					.getIdentifier(imgName, "drawable", context.getPackageName()));
			imageView.setImageDrawable(drawable);
		}
		catch (Exception e)
		{
//			Log.d("ImageName:", ss[ss.length -1]);
		}

	}

	public void fbSharePhoto(Activity activity, android.support.v4.app.Fragment fragment)
	{
		ParseUser user = ParseUser.getCurrentUser();
		if(user == null)
		{
			SocialLoginService fbService = SocialLoginService.getInstance(activity);
			fbService.facebookLogin(activity, fragment);
		}
		else
		{
			SharePhoto photo = new SharePhoto.Builder()
					.setBitmap(Reg.ptLastImage)
					.setCaption("Welcome To Facebook Photo Sharing on Cambro Product!")
					.build();

			SharePhotoContent content = new SharePhotoContent.Builder()
					.addPhoto(photo)
					.build();
			ShareDialog shareDialog = new ShareDialog(activity);
			shareDialog.show(activity, content);
		}
	}

	public void sendDefaultEmail(Activity activity, String category, String email)
	{
		File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "cambro_product.jpg");
		Uri URI = Uri.fromFile(filelocation);

		try {
//            email = "";
			String subject = "Personalize Tool";
			String body = "";

			body += "Product Name: " + category.toUpperCase() + " TRAY\n";
			body += "Product Code: " + ((PersonalizeToolActivity) activity).getProductCode() + "\n";
			body += "Size: " + ((PersonalizeToolActivity) activity).getSize() + "\n";
			body += "Dimension: " + ((PersonalizeToolActivity) activity).getDimension() + "\n";
			body += "Quantity: ";

			final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			emailIntent.setType("plain/text");
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email});
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
			if (URI != null) {
				emailIntent.putExtra(Intent.EXTRA_STREAM, URI);
			}
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
			activity.startActivity(Intent.createChooser(emailIntent,"Sending email..."));
		} catch (Throwable t) {
			Toast.makeText(activity,"Request failed try again: " + t.toString(),Toast.LENGTH_LONG).show();
		}
	}

	public boolean saveImageToInternalStorage(Bitmap image) {

		try {
			File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "cambro_tray_product.jpg");
			try {
				OutputStream fOut = null;
				fOut = new FileOutputStream(filelocation);
				image.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
				fOut.flush();
				fOut.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}
}


