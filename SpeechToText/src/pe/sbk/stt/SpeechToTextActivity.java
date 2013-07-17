package pe.sbk.stt;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class SpeechToTextActivity extends Activity implements OnClickListener {
	private final int GOOGLE_STT = 1000, MY_UI=1001;				//requestCode. ���������ν�, ���� ���� Activity
	private ArrayList<String> mResult;									//�����ν� ��� ������ list
	private String mSelectedString;										//��� list �� ����ڰ� ������ �ؽ�Ʈ
	private TextView mResultTextView;									//���� ��� ����ϴ� �ؽ�Ʈ ��
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		findViewById(R.id.show).setOnClickListener(this);				//���� �����ν� �� �̿�.
		findViewById(R.id.hide).setOnClickListener(this);				//���� ���� activity �̿�.
		
		mResultTextView = (TextView)findViewById(R.id.result);		//��� ��� ��
	}

	@Override
	public void onClick(View v) {
		int view = v.getId();
		
		if(view == R.id.show){		//���� �����ν� �� ����̸�
			Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);			//intent ����
			i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());	//�����ν��� ȣ���� ��Ű��
			i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");							//�����ν� ��� ����
			i.putExtra(RecognizerIntent.EXTRA_PROMPT, "���� �ϼ���.");						//����ڿ��� ���� �� ����
			
			startActivityForResult(i, GOOGLE_STT);												//���� �����ν� ����
		}
		else if(view == R.id.hide){
			startActivityForResult(new Intent(this, CustomUIActivity.class), MY_UI);			//���� ���� activity ����
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if( resultCode == RESULT_OK  && (requestCode == GOOGLE_STT || requestCode == MY_UI) ){		//����� ������
			showSelectDialog(requestCode, data);				//����� ���̾�α׷� ���.
		}
		else{															//����� ������ ���� �޽��� ���
			String msg = null;
			
			//���� ���� activity���� �Ѿ���� ���� �ڵ带 �з�
			switch(resultCode){
				case SpeechRecognizer.ERROR_AUDIO:
					msg = "����� �Է� �� ������ �߻��߽��ϴ�.";
					break;
				case SpeechRecognizer.ERROR_CLIENT:
					msg = "�ܸ����� ������ �߻��߽��ϴ�.";
					break;
				case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
					msg = "������ �����ϴ�.";
					break;
				case SpeechRecognizer.ERROR_NETWORK:
				case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
					msg = "��Ʈ��ũ ������ �߻��߽��ϴ�.";
					break;
				case SpeechRecognizer.ERROR_NO_MATCH:
					msg = "��ġ�ϴ� �׸��� �����ϴ�.";
					break;
				case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
					msg = "�����ν� ���񽺰� ������ �Ǿ����ϴ�.";
					break;
				case SpeechRecognizer.ERROR_SERVER:
					msg = "�������� ������ �߻��߽��ϴ�.";
					break;
				case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
					msg = "�Է��� �����ϴ�.";
					break;
			}
			
			if(msg != null)		//���� �޽����� null�� �ƴϸ� �޽��� ���
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
		}
	}
	
	//��� list ����ϴ� ���̾�α� ����
	private void showSelectDialog(int requestCode, Intent data){
		String key = "";
		if(requestCode == GOOGLE_STT)					//���������ν��̸�
			key = RecognizerIntent.EXTRA_RESULTS;	//Ű�� ����
		else if(requestCode == MY_UI)					//���� ���� activity �̸�
			key = SpeechRecognizer.RESULTS_RECOGNITION;	//Ű�� ����
		
		mResult = data.getStringArrayListExtra(key);		//�νĵ� ������ list �޾ƿ�.
		String[] result = new String[mResult.size()];			//�迭����. ���̾�α׿��� ����ϱ� ����
		mResult.toArray(result);									//	list �迭�� ��ȯ
		
		//1�� �����ϴ� ���̾�α� ����
		AlertDialog ad = new AlertDialog.Builder(this).setTitle("�����ϼ���.")
							.setSingleChoiceItems(result, -1, new DialogInterface.OnClickListener() {
								@Override public void onClick(DialogInterface dialog, int which) {
										mSelectedString = mResult.get(which);		//�����ϸ� �ش� ���� ����
								}
							})
							.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
								@Override public void onClick(DialogInterface dialog, int which) {
									mResultTextView.setText("�νİ�� : "+mSelectedString);		//Ȯ�� ��ư ������ ��� ���
								}
							})
							.setNegativeButton("���", new DialogInterface.OnClickListener() {
								@Override public void onClick(DialogInterface dialog, int which) {
									mResultTextView.setText("");		//��ҹ�ư ������ �ʱ�ȭ
									mSelectedString = null;
								}
							}).create();
		ad.show();
	}
}