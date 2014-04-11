package com.hyrt.cnp.dynamic.ui;

import android.os.Bundle;
import android.support.v7.internal.view.SupportMenuInflater;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.dynamic.R;
import com.hyrt.cnp.dynamic.request.MyAddAblumRequest;
import com.hyrt.cnp.dynamic.requestListener.MyAddAblumRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by Zoe on 2014-04-11.
 */
public class AddAlbumActivity extends BaseActivity{

    private EditText etDescribe;
    private EditText etName;
    private MenuItem sendbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_album);
        findView();
        setListener();
    }

    private void setListener() {
        etDescribe.addTextChangedListener(mEditTextChangeListener);
        etName.addTextChangedListener(mEditTextChangeListener);
    }

    TextWatcher mEditTextChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) { }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if(etDescribe.getText().length() > 0 && etName.getText().length() > 0){
                sendbtn.setEnabled(true);
            }else{
                sendbtn.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) { }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SupportMenuInflater mMenuInflater = new SupportMenuInflater(this);
        mMenuInflater.inflate(R.menu.senddy, menu);
        menu.findItem(R.id.senddy).
                setCheckable(false).
                setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        sendbtn = menu.findItem(R.id.senddy);
        sendbtn.setTitle("确定");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("确定")){
            MyAddAblumRequestListener requestListener = new MyAddAblumRequestListener(this);
            requestListener.setListener(mAddAblumRequestListener);
            MyAddAblumRequest request = new MyAddAblumRequest(
                    Comment.Model3.class, this,
                    etName.getText().toString(),
                    etDescribe.getText().toString());
            spiceManager.execute(request, request.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                    requestListener.start());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    MyAddAblumRequestListener.RequestListener mAddAblumRequestListener = new MyAddAblumRequestListener.RequestListener() {
        @Override
        public void onRequestSuccess(Object data) {
            setResult(ChangeAlbumActivity.RESULT_FOR_ADD_ALBUM);
            finish();
        }

        @Override
        public void onRequestFailure(SpiceException e) {

        }
    };

    private void findView() {
        etName = (EditText) findViewById(R.id.et_album_name);
        etDescribe = (EditText) findViewById(R.id.et_album_describe);
    }
}
