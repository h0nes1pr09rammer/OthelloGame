package com.lzq.othellogame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class GameView extends SurfaceView{

	private int screenWidth;
	private int screenHeight;
	private int paddingTopAndBottom;
	private int chessPieceLength;
	private int chessPieceRadius;
	private int touchX;
	private int touchY;
	private int lines;
	private int columns;
	private boolean isFirst = true;
	private int chessBoard[][];
	private boolean isBlacked = false;
	private TextShow textShow;
	private Paint leftPaint;
	private Paint rightPaint;
	private Paint slidePaint;
	private Paint textPaint;
	private int tabCount;
	private Context context;
	
	public interface TextShow
	{
		void textShow(int a);
	}
	public void setTextShow(TextShow textShow)
	{
		this.textShow = textShow;
	}
	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.GameView);
		tabCount = a.getInt(R.styleable.GameView_tab_count,8);
		a.recycle();
		// TODO Auto-generated constructor stub
		init();
	}
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		// TODO Auto-generated constructor stub
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.GameView);
		tabCount = a.getInt(R.styleable.GameView_tab_count,8);
		a.recycle();
		init();
	}
	public GameView(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
		init();
	}
	private void init(){
		isFirst = true;
		chessBoard = new int[tabCount][tabCount];
		textPaint = new Paint();
		textPaint.setTextSize(50);
		textPaint.setTextAlign(Align.CENTER);
		textPaint.setColor(Color.BLACK);
		textPaint.setStyle(Paint.Style.STROKE);
		textPaint.setStrokeWidth(5);

		leftPaint = new Paint();
		leftPaint.setAntiAlias(true);
		leftPaint.setColor(Color.BLACK);
		leftPaint.setStrokeWidth(1);

		rightPaint = new Paint();
		rightPaint.setAntiAlias(true);
		rightPaint.setColor(Color.WHITE);
		rightPaint.setStrokeWidth(1);

		slidePaint = new Paint();
		slidePaint.setStyle(Paint.Style.STROKE);
		slidePaint.setColor(Color.RED);
		slidePaint.setStrokeWidth(5);
		GameUtils.init(chessBoard,tabCount);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		screenWidth = MeasureSpec.getSize(widthMeasureSpec);
		screenHeight = MeasureSpec.getSize(heightMeasureSpec);
		paddingTopAndBottom = (screenHeight - screenWidth) / 2;
		chessPieceLength = screenWidth  / tabCount;
		chessPieceRadius = chessPieceLength  / 2;
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Log.i("lzq"," start Draw");

		for (int i = 0; i <= tabCount; i++) {
			canvas.drawLine(chessPieceLength *i, paddingTopAndBottom, chessPieceLength *i, screenHeight - paddingTopAndBottom, leftPaint);
			canvas.drawLine(0, paddingTopAndBottom + chessPieceLength *i, screenWidth, paddingTopAndBottom + chessPieceLength *i, leftPaint);
		}
		if (isFirst) {
			canvas.drawText("白方先行", screenWidth /2, paddingTopAndBottom /2, leftPaint);
			isFirst = false;
		}else if (isBlacked) {
			canvas.drawText("黑方下", screenWidth /2, paddingTopAndBottom /2, leftPaint);
		}else{
			canvas.drawText("白方下", screenWidth /2, paddingTopAndBottom /2, leftPaint);
		}
	
		for (int i = 0; i < tabCount; i++) {
			for (int j = 0; j < tabCount; j++) {
				if (chessBoard[i][j]==1) {
					Log.i("lzq","绘制白棋"+chessPieceRadius);
					canvas.drawCircle(chessPieceRadius + chessPieceLength *i, paddingTopAndBottom + chessPieceRadius + chessPieceLength *j, chessPieceRadius *0.9f, leftPaint);
				}else if(chessBoard[i][j]==2)
				{
					Log.i("lzq","绘制黑棋"+chessPieceRadius);
					canvas.drawCircle(chessPieceRadius + chessPieceLength *i, paddingTopAndBottom + chessPieceRadius + chessPieceLength *j, chessPieceRadius *0.9f, rightPaint);
				}
			}
		}
		RectF rectF = new RectF(chessPieceLength *columns,paddingTopAndBottom+chessPieceLength *lines,chessPieceLength *(columns+1),paddingTopAndBottom+chessPieceLength *(lines+1));
		canvas.drawRect(rectF,slidePaint);
	}
	@SuppressLint("NewApi") @Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touchX =(int) event.getX();
			touchY =(int) event.getY();
			if(touchY >= paddingTopAndBottom && touchY <= paddingTopAndBottom + screenWidth)
			{
				lines = (touchY - paddingTopAndBottom)/ chessPieceLength;
				columns = touchX / chessPieceLength;
				if(!isBlacked)
				{
					if(GameUtils.removeUp(columns, lines, 2))
					{
						isBlacked = true;
					}else{
						showMsg(4);
					}
					
				}else
				{
					if(GameUtils.removeUp(columns, lines, 1))
					{
						isBlacked = false;
					}
					else{
						showMsg(4);
					}
				}
			}
			if (GameUtils.end()==2) {
				showMsg(2);
				GameUtils.init(chessBoard,tabCount);
			}
			if(GameUtils.end()==1)
			{
				showMsg(1);
				GameUtils.init(chessBoard,tabCount);
			}
			if(GameUtils.end()==3){
				showMsg(3);
				GameUtils.init(chessBoard,tabCount);
			}
			postInvalidate();
			break;
		}
		return super.onTouchEvent(event);
	}
	private void showMsg(int code){
		if (textShow!=null) {
			textShow.textShow(code);
		}
	}
	
}
