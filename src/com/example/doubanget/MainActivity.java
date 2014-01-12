package com.example.doubanget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Entity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText etSearch;
	private Button btnSearch;
	private Button btnNext;
	private Button btnLast;
	private TextView tvBookName;
	private TextView tvISBN;
	private TextView tvAuthor;
	private TextView tvPress;
	private TextView tvPrice;
	private TextView tvNo1;
	private TextView tvNo2;
	private TextView tvNo3;
	private TextView tvNo4;
	private TextView tvNo5;
	private TextView tvSummary;
	private TextView tvComment;
	private ImageView imgBook;
	private SpannableString ss1;
	private SpannableString ss2;
	private SpannableString ss3;
	private SpannableString ss4;
	private SpannableString ss5;
	private String NO1="";
	private String NO2="";
	private String NO3="";
	private String NO4="";
	private String NO5="";
	private int a=0;
	private int hold=0;
	HttpResponse httpResponse=null;
	private String searchUrl="https://api.douban.com/v2/book/search?q=";
	private String bookUrl="https://api.douban.com/v2/book/";
			
	private List<Book> books;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgBook=(ImageView)this.findViewById(R.id.imgBook);
        etSearch=(EditText)this.findViewById(R.id.etSearch);
        btnSearch=(Button)this.findViewById(R.id.btnSearch);
        btnNext=(Button)this.findViewById(R.id.btnNext);
        btnLast=(Button)this.findViewById(R.id.btnLast);
        
        tvBookName=(TextView)this.findViewById(R.id.tvBookName);
        tvISBN=(TextView)this.findViewById(R.id.tvISBN);
        tvAuthor=(TextView)this.findViewById(R.id.tvAuthor);
        tvPress=(TextView)this.findViewById(R.id.tvPress);
        tvPrice=(TextView)this.findViewById(R.id.tvPrice);
        tvNo1=(TextView)this.findViewById(R.id.tvNo1);
        tvNo2=(TextView)this.findViewById(R.id.tvNo2);
        tvNo3=(TextView)this.findViewById(R.id.tvNo3);
        tvNo4=(TextView)this.findViewById(R.id.tvNo4);
        tvNo5=(TextView)this.findViewById(R.id.tvNo5);
        
        tvSummary=(TextView)this.findViewById(R.id.tvSummary);
        tvComment=(TextView)this.findViewById(R.id.tvComment);
        
        btnSearch.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				String bookName=etSearch.getText().toString();
				//请求http数据，按关键词搜索排名TextNo
				String getData=searchUrl+bookName+"&fields=id,title";
				String InData=new NetHelper().getData(getData);
				JSONObject dataJson;
				JSONArray data;
				books=new ArrayList<Book>();
				try {
					dataJson = new JSONObject(InData);
					Log.d("TTT", "xxx");
					data = dataJson.getJSONArray("books");
					
					for(int i=0;i<data.length();i++){
						
						Book book=new Book();
						
						String title=data.getJSONObject(i).getString("title");
						String id=data.getJSONObject(i).getString("id");
						Log.d("TTT", "sss");
						book.setId(id);
						book.setTitle(title);
						books.add(book);
					}
					Log.d("TTT", "aaa");
					NO1=(a+1)+"、"+books.get(a).getTitle();
					
					NO2=(a+2)+"、"+books.get(a+1).getTitle();
					
					NO3=(a+3)+"、"+books.get(a+2).getTitle();
					
					NO4=(a+4)+"、"+books.get(a+3).getTitle();
					
					NO5=(a+5)+"、"+books.get(a+4).getTitle();
					//绑定spannable对象，点击触发事件响应
					ss1=new SpannableString(NO1);
			        ss2=new SpannableString(NO2);
			        ss3=new SpannableString(NO3);
			        ss4=new SpannableString(NO4);
			        ss5=new SpannableString(NO5);
			        
			        ss1.setSpan(new ClickableSpan() {
						
						@Override
						public void onClick(View widget) {
							//点击相应事件，以URLConnction方式请求http资源，返回图书信息
							String bUrl=bookUrl+books.get(a).getId()+"?fields=title,,author,isbn10,image,publisher,price,summary";
							String getBookDate=new NetHelper().getData(bUrl);
							try {
								JSONObject bookJSON=new JSONObject(getBookDate);
								String title=bookJSON.getString("title");
								String author=bookJSON.getString("author");
								String isbn=bookJSON.getString("isbn10");
								String price=bookJSON.getString("price");
								String press=bookJSON.getString("publisher");
								String summay=bookJSON.getString("summary");
								String imageUrl=bookJSON.getString("image");
								Bitmap bm = null;
								try {
									bm=new NetHelper().getImage(imageUrl);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								imgBook.setImageBitmap(bm);
								tvSummary.setText("简介： "+summay);
								tvBookName.setText("书名："+title);
								tvAuthor.setText("作者："+author);
								tvISBN.setText("isbn："+isbn);
								tvPress.setText("出版社："+press);
								tvPrice.setText("价格："+price);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}, 0, NO1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			        
			        ss2.setSpan(new ClickableSpan() {
						
						@Override
						public void onClick(View widget) {
							String bUrl=bookUrl+books.get(a+1).getId()+"?fields=title,,author,isbn10,image,publisher,price,summary";
							String getBookDate=new NetHelper().getData(bUrl);
							try {
								JSONObject bookJSON=new JSONObject(getBookDate);
								String title=bookJSON.getString("title");
								String author=bookJSON.getString("author");
								String isbn=bookJSON.getString("isbn10");
								String price=bookJSON.getString("price");
								String press=bookJSON.getString("publisher");
								String summay=bookJSON.getString("summary");
								String imageUrl=bookJSON.getString("image");
								Bitmap bm = null;
								try {
									bm=new NetHelper().getImage(imageUrl);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								imgBook.setImageBitmap(bm);
								tvSummary.setText("简介： "+summay);
								tvBookName.setText("书名："+title);
								tvAuthor.setText("作者："+author);
								tvISBN.setText("isbn："+isbn);
								tvPress.setText("出版社："+press);
								tvPrice.setText("价格："+price);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}, 0, NO2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			        
			        ss3.setSpan(new ClickableSpan() {
						
						@Override
						public void onClick(View widget) {
							String bUrl=bookUrl+books.get(a+2).getId()+"?fields=title,,author,isbn10,image,publisher,price,summary";
							String getBookDate=new NetHelper().getData(bUrl);
							try {
								JSONObject bookJSON=new JSONObject(getBookDate);
								String title=bookJSON.getString("title");
								String author=bookJSON.getString("author");
								String isbn=bookJSON.getString("isbn10");
								String price=bookJSON.getString("price");
								String press=bookJSON.getString("publisher");
								String summay=bookJSON.getString("summary");
								String imageUrl=bookJSON.getString("image");
								Bitmap bm = null;
								try {
									bm=new NetHelper().getImage(imageUrl);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								imgBook.setImageBitmap(bm);
								tvSummary.setText("简介： "+summay);
								tvBookName.setText("书名："+title);
								tvAuthor.setText("作者："+author);
								tvISBN.setText("isbn："+isbn);
								tvPress.setText("出版社："+press);
								tvPrice.setText("价格："+price);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}, 0, NO3.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			        
			        ss4.setSpan(new ClickableSpan() {
						
						@Override
						public void onClick(View widget) {
							String bUrl=bookUrl+books.get(a+3).getId()+"?fields=title,,author,isbn10,image,publisher,price,summary";
							String getBookDate=new NetHelper().getData(bUrl);
							try {
								JSONObject bookJSON=new JSONObject(getBookDate);
								String title=bookJSON.getString("title");
								String author=bookJSON.getString("author");
								String isbn=bookJSON.getString("isbn10");
								String price=bookJSON.getString("price");
								String press=bookJSON.getString("publisher");
								String summay=bookJSON.getString("summary");
								String imageUrl=bookJSON.getString("image");
								Bitmap bm = null;
								try {
									bm=new NetHelper().getImage(imageUrl);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								imgBook.setImageBitmap(bm);
								tvSummary.setText("简介："+summay);
								tvBookName.setText("书名："+title);
								tvAuthor.setText("作者："+author);
								tvISBN.setText("isbn："+isbn);
								tvPress.setText("出版社："+press);
								tvPrice.setText("价格："+price);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}, 0, NO4.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

			        ss5.setSpan(new ClickableSpan() {
						
						@Override
						public void onClick(View widget) {
							String bUrl=bookUrl+books.get(a+4).getId()+"?fields=title,,author,isbn10,image,publisher,price,summary";
							String getBookDate=new NetHelper().getData(bUrl);
							try {
								JSONObject bookJSON=new JSONObject(getBookDate);
								String title=bookJSON.getString("title");
								String author=bookJSON.getString("author");
								String isbn=bookJSON.getString("isbn10");
								String price=bookJSON.getString("price");
								String press=bookJSON.getString("publisher");
								String summay=bookJSON.getString("summary");
								String imageUrl=bookJSON.getString("image");
								Bitmap bm = null;
								try {
									bm=new NetHelper().getImage(imageUrl);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								imgBook.setImageBitmap(bm);
								tvSummary.setText("简介"+summay);
								tvBookName.setText("书名："+title);
								tvAuthor.setText("作者："+author);
								tvISBN.setText("isbn："+isbn);
								tvPress.setText("出版社："+press);
								tvPrice.setText("价格："+price);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}		
						}
					}, 0, NO5.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			        
					tvNo1.setText(ss1);
					tvNo2.setText(ss2);
					tvNo3.setText(ss3);
					tvNo4.setText(ss4);
					tvNo5.setText(ss5);
					tvNo1.setMovementMethod(LinkMovementMethod.getInstance());
					tvNo2.setMovementMethod(LinkMovementMethod.getInstance());
					tvNo3.setMovementMethod(LinkMovementMethod.getInstance());
					tvNo4.setMovementMethod(LinkMovementMethod.getInstance());
					tvNo5.setMovementMethod(LinkMovementMethod.getInstance());
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
        
        btnNext.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if(hold==1){
					Toast.makeText(MainActivity.this, "已经是最后一页，请选择其他方式", Toast.LENGTH_SHORT).show();
				}
				else{
					hold++;
					a+=5;
					NO1=(a+1)+"、"+books.get(a).getTitle();
					
					NO2=(a+2)+"、"+books.get(a+1).getTitle();
					
					NO3=(a+3)+"、"+books.get(a+2).getTitle();
					
					NO4=(a+4)+"、"+books.get(a+3).getTitle();
					
					NO5=(a+5)+"、"+books.get(a+4).getTitle();
					
					ss1=new SpannableString(NO1);
			        ss2=new SpannableString(NO2);
			        ss3=new SpannableString(NO3);
			        ss4=new SpannableString(NO4);
			        ss5=new SpannableString(NO5);
			        
			        ss1.setSpan(new ClickableSpan() {
						
						@Override
						public void onClick(View widget) {
							//点击相应事件，请求http资源，返回图书信息
							String bUrl=bookUrl+books.get(a).getId()+"?fields=title,,author,isbn10,image,publisher,price,summary";
							String getBookDate=new NetHelper().getData(bUrl);
							try {
								JSONObject bookJSON=new JSONObject(getBookDate);
								String title=bookJSON.getString("title");
								String author=bookJSON.getString("author");
								String isbn=bookJSON.getString("isbn10");
								String price=bookJSON.getString("price");
								String press=bookJSON.getString("publisher");
								String summay=bookJSON.getString("summary");
								String imageUrl=bookJSON.getString("image");
								Bitmap bm = null;
								try {
									bm=new NetHelper().getImage(imageUrl);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								imgBook.setImageBitmap(bm);
								tvSummary.setText("简介： "+summay);
								tvBookName.setText("书名："+title);
								tvAuthor.setText("作者："+author);
								tvISBN.setText("isbn："+isbn);
								tvPress.setText("出版社："+press);
								tvPrice.setText("价格："+price);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}, 0, NO1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			        
			        ss2.setSpan(new ClickableSpan() {
						
						@Override
						public void onClick(View widget) {
							String bUrl=bookUrl+books.get(a+1).getId()+"?fields=title,,author,isbn10,image,publisher,price,summary";
							String getBookDate=new NetHelper().getData(bUrl);
							try {
								JSONObject bookJSON=new JSONObject(getBookDate);
								String title=bookJSON.getString("title");
								String author=bookJSON.getString("author");
								String isbn=bookJSON.getString("isbn10");
								String price=bookJSON.getString("price");
								String press=bookJSON.getString("publisher");
								String summay=bookJSON.getString("summary");
								String imageUrl=bookJSON.getString("image");
								Bitmap bm = null;
								try {
									bm=new NetHelper().getImage(imageUrl);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								imgBook.setImageBitmap(bm);
								tvSummary.setText("简介： "+summay);
								tvBookName.setText("书名："+title);
								tvAuthor.setText("作者："+author);
								tvISBN.setText("isbn："+isbn);
								tvPress.setText("出版社："+press);
								tvPrice.setText("价格："+price);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}, 0, NO2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			        
			        ss3.setSpan(new ClickableSpan() {
						
						@Override
						public void onClick(View widget) {
							String bUrl=bookUrl+books.get(a+2).getId()+"?fields=title,,author,isbn10,image,publisher,price,summary";
							String getBookDate=new NetHelper().getData(bUrl);
							try {
								JSONObject bookJSON=new JSONObject(getBookDate);
								String title=bookJSON.getString("title");
								String author=bookJSON.getString("author");
								String isbn=bookJSON.getString("isbn10");
								String price=bookJSON.getString("price");
								String press=bookJSON.getString("publisher");
								String summay=bookJSON.getString("summary");
								String imageUrl=bookJSON.getString("image");
								Bitmap bm = null;
								try {
									bm=new NetHelper().getImage(imageUrl);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								imgBook.setImageBitmap(bm);
								tvSummary.setText("简介： "+summay);
								tvBookName.setText("书名："+title);
								tvAuthor.setText("作者："+author);
								tvISBN.setText("isbn："+isbn);
								tvPress.setText("出版社："+press);
								tvPrice.setText("价格："+price);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}, 0, NO3.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			        
			        ss4.setSpan(new ClickableSpan() {
						
						@Override
						public void onClick(View widget) {
							String bUrl=bookUrl+books.get(a+3).getId()+"?fields=title,,author,isbn10,image,publisher,price,summary";
							String getBookDate=new NetHelper().getData(bUrl);
							try {
								JSONObject bookJSON=new JSONObject(getBookDate);
								String title=bookJSON.getString("title");
								String author=bookJSON.getString("author");
								String isbn=bookJSON.getString("isbn10");
								String price=bookJSON.getString("price");
								String press=bookJSON.getString("publisher");
								String summay=bookJSON.getString("summary");
								String imageUrl=bookJSON.getString("image");
								Bitmap bm = null;
								try {
									bm=new NetHelper().getImage(imageUrl);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								imgBook.setImageBitmap(bm);
								tvSummary.setText("简介： "+summay);
								tvBookName.setText("书名："+title);
								tvAuthor.setText("作者："+author);
								tvISBN.setText("isbn："+isbn);
								tvPress.setText("出版社："+press);
								tvPrice.setText("价格："+price);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}, 0, NO4.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

			        ss5.setSpan(new ClickableSpan() {
						
						@Override
						public void onClick(View widget) {
							String bUrl=bookUrl+books.get(a+4).getId()+"?fields=title,,author,isbn10,image,publisher,price,summary";
							String getBookDate=new NetHelper().getData(bUrl);
							try {
								JSONObject bookJSON=new JSONObject(getBookDate);
								String title=bookJSON.getString("title");
								String author=bookJSON.getString("author");
								String isbn=bookJSON.getString("isbn10");
								String price=bookJSON.getString("price");
								String press=bookJSON.getString("publisher");
								String summay=bookJSON.getString("summary");
								String imageUrl=bookJSON.getString("image");
								Bitmap bm = null;
								try {
									bm=new NetHelper().getImage(imageUrl);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								imgBook.setImageBitmap(bm);
								tvSummary.setText("简介： "+summay);
								tvBookName.setText("书名："+title);
								tvAuthor.setText("作者："+author);
								tvISBN.setText("isbn："+isbn);
								tvPress.setText("出版社："+press);
								tvPrice.setText("价格："+price);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}		
						}
					}, 0, NO5.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			        
					tvNo1.setText(ss1);
					tvNo2.setText(ss2);
					tvNo3.setText(ss3);
					tvNo4.setText(ss4);
					tvNo5.setText(ss5);
					tvNo1.setMovementMethod(LinkMovementMethod.getInstance());
					tvNo2.setMovementMethod(LinkMovementMethod.getInstance());
					tvNo3.setMovementMethod(LinkMovementMethod.getInstance());
					tvNo4.setMovementMethod(LinkMovementMethod.getInstance());
					tvNo5.setMovementMethod(LinkMovementMethod.getInstance());
				}
				
			}
		});
        
        btnLast.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if(hold==0){
					Toast.makeText(MainActivity.this, "已是最前一页，请做出其他选择", Toast.LENGTH_SHORT).show();
				}
				else{
					hold--;
					a-=5;
					NO1=(a+1)+"、"+books.get(a).getTitle();
					
					NO2=(a+2)+"、"+books.get(a+1).getTitle();
					
					NO3=(a+3)+"、"+books.get(a+2).getTitle();
					
					NO4=(a+4)+"、"+books.get(a+3).getTitle();
					
					NO5=(a+5)+"、"+books.get(a+4).getTitle();
					
					ss1=new SpannableString(NO1);
			        ss2=new SpannableString(NO2);
			        ss3=new SpannableString(NO3);
			        ss4=new SpannableString(NO4);
			        ss5=new SpannableString(NO5);
			        
			        ss1.setSpan(new ClickableSpan() {
						
						@Override
						public void onClick(View widget) {
							//点击相应事件，请求http资源，返回图书信息
							String bUrl=bookUrl+books.get(a).getId()+"?fields=title,,author,isbn10,image,publisher,price,summary";
							String getBookDate=new NetHelper().getData(bUrl);
							try {
								JSONObject bookJSON=new JSONObject(getBookDate);
								String title=bookJSON.getString("title");
								String author=bookJSON.getString("author");
								String isbn=bookJSON.getString("isbn10");
								String price=bookJSON.getString("price");
								String press=bookJSON.getString("publisher");
								String summay=bookJSON.getString("summary");
								String imageUrl=bookJSON.getString("image");
								Bitmap bm = null;
								try {
									bm=new NetHelper().getImage(imageUrl);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								imgBook.setImageBitmap(bm);
								tvSummary.setText("简介： "+summay);
								tvBookName.setText("书名："+title);
								tvAuthor.setText("作者："+author);
								tvISBN.setText("isbn："+isbn);
								tvPress.setText("出版社："+press);
								tvPrice.setText("价格："+price);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}, 0, NO1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			        
			        ss2.setSpan(new ClickableSpan() {
						
						@Override
						public void onClick(View widget) {
							String bUrl=bookUrl+books.get(a+1).getId()+"?fields=title,,author,isbn10,image,publisher,price,summary";
							String getBookDate=new NetHelper().getData(bUrl);
							try {
								JSONObject bookJSON=new JSONObject(getBookDate);
								String title=bookJSON.getString("title");
								String author=bookJSON.getString("author");
								String isbn=bookJSON.getString("isbn10");
								String price=bookJSON.getString("price");
								String press=bookJSON.getString("publisher");
								String summay=bookJSON.getString("summary");
								String imageUrl=bookJSON.getString("image");
								Bitmap bm = null;
								try {
									bm=new NetHelper().getImage(imageUrl);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								imgBook.setImageBitmap(bm);
								tvSummary.setText("简介： "+summay);
								tvBookName.setText("书名："+title);
								tvAuthor.setText("作者："+author);
								tvISBN.setText("isbn："+isbn);
								tvPress.setText("出版社："+press);
								tvPrice.setText("价格："+price);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}, 0, NO2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			        
			        ss3.setSpan(new ClickableSpan() {
						
						@Override
						public void onClick(View widget) {
							String bUrl=bookUrl+books.get(a+2).getId()+"?fields=title,,author,isbn10,image,publisher,price,summary";
							String getBookDate=new NetHelper().getData(bUrl);
							try {
								JSONObject bookJSON=new JSONObject(getBookDate);
								String title=bookJSON.getString("title");
								String author=bookJSON.getString("author");
								String isbn=bookJSON.getString("isbn10");
								String price=bookJSON.getString("price");
								String press=bookJSON.getString("publisher");
								String summay=bookJSON.getString("summary");
								String imageUrl=bookJSON.getString("image");
								Bitmap bm = null;
								try {
									bm=new NetHelper().getImage(imageUrl);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								imgBook.setImageBitmap(bm);
								tvSummary.setText("简介： "+summay);
								tvBookName.setText("书名："+title);
								tvAuthor.setText("作者："+author);
								tvISBN.setText("isbn："+isbn);
								tvPress.setText("出版社："+press);
								tvPrice.setText("价格："+price);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}, 0, NO3.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			        
			        ss4.setSpan(new ClickableSpan() {
						
						@Override
						public void onClick(View widget) {
							String bUrl=bookUrl+books.get(a+3).getId()+"?fields=title,,author,isbn10,image,publisher,price,summary";
							String getBookDate=new NetHelper().getData(bUrl);
							try {
								JSONObject bookJSON=new JSONObject(getBookDate);
								String title=bookJSON.getString("title");
								String author=bookJSON.getString("author");
								String isbn=bookJSON.getString("isbn10");
								String price=bookJSON.getString("price");
								String press=bookJSON.getString("publisher");
								String summay=bookJSON.getString("summary");
								String imageUrl=bookJSON.getString("image");
								Bitmap bm = null;
								try {
									bm=new NetHelper().getImage(imageUrl);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								imgBook.setImageBitmap(bm);
								tvSummary.setText("简介： "+summay);
								tvBookName.setText("书名："+title);
								tvAuthor.setText("作者："+author);
								tvISBN.setText("isbn："+isbn);
								tvPress.setText("出版社："+press);
								tvPrice.setText("价格："+price);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}, 0, NO4.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

			        ss5.setSpan(new ClickableSpan() {
						
						@Override
						public void onClick(View widget) {
							String bUrl=bookUrl+books.get(a+4).getId()+"?fields=title,,author,isbn10,image,publisher,price,summary";
							String getBookDate=new NetHelper().getData(bUrl);
							try {
								JSONObject bookJSON=new JSONObject(getBookDate);
								String title=bookJSON.getString("title");
								String author=bookJSON.getString("author");
								String isbn=bookJSON.getString("isbn10");
								String price=bookJSON.getString("price");
								String press=bookJSON.getString("publisher");
								String summay=bookJSON.getString("summary");
								String imageUrl=bookJSON.getString("image");
								Bitmap bm = null;
								try {
									bm=new NetHelper().getImage(imageUrl);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								imgBook.setImageBitmap(bm);
								tvSummary.setText("简介： "+summay);
								tvBookName.setText("书名："+title);
								tvAuthor.setText("作者："+author);
								tvISBN.setText("isbn："+isbn);
								tvPress.setText("出版社："+press);
								tvPrice.setText("价格："+price);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}		
						}
					}, 0, NO5.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			        
					tvNo1.setText(ss1);
					tvNo2.setText(ss2);
					tvNo3.setText(ss3);
					tvNo4.setText(ss4);
					tvNo5.setText(ss5);
					tvNo1.setMovementMethod(LinkMovementMethod.getInstance());
					tvNo2.setMovementMethod(LinkMovementMethod.getInstance());
					tvNo3.setMovementMethod(LinkMovementMethod.getInstance());
					tvNo4.setMovementMethod(LinkMovementMethod.getInstance());
					tvNo5.setMovementMethod(LinkMovementMethod.getInstance());
				}
			}
		});
        
        
        
       
		
		
      
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
//需要处理线程，控制屏幕刷新和信息处理
