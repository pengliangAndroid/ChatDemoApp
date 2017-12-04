package com.wstro.chatdemoapp;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wstro.app.common.utils.CommonUtils;
import com.wstro.app.common.utils.DateUtils;
import com.wstro.chatdemoapp.model.ChatRecordInfo;
import com.wstro.chatdemoapp.model.ChatUserInfo;
import com.wstro.chatdemoapp.model.MsgType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class CustomChatAdapter extends CustomMultiItemAdapter<ChatRecordInfo> {
    private static final int TYPE_FROM_ME = 1;
    private static final int TYPE_FROM_OTHER = 2;

    private Context context;
    private ChatUserInfo chatUserInfo;

    private String myUserName;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public CustomChatAdapter(Context context, ChatUserInfo chatUserInfo, List<ChatRecordInfo> data) {
        super(data);
        addItemType(TYPE_FROM_ME, R.layout.chat_item_list_right);
        addItemType(TYPE_FROM_OTHER, R.layout.chat_item_list_left);

        this.context = context;

        this.chatUserInfo = chatUserInfo;
        //this.accountInfoMap = map;
        //this.myUserName = AppData.get().getUserInfo().getLoginName();
        this.myUserName = "1";
    }


    @Override
    protected int getItemType(int position) {
        if (mData.get(position).getSenderId().equals(myUserName)) {
            return TYPE_FROM_ME;
        } else {
            return TYPE_FROM_OTHER;
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, ChatRecordInfo item) {
        convertLayout(helper, item);
    }

    private void convertLayout(final BaseViewHolder helper, final ChatRecordInfo item) {
        int position = helper.getLayoutPosition();
        int type = getItemType(position);

        TextView dateText = helper.getView(R.id.chat_item_date);
        TextView userText = helper.getView(R.id.tv_username);
        ImageView civAvatar = helper.getView(R.id.chat_item_avatar);

        String imageId = null,userName = null;
        if(type == TYPE_FROM_ME){
            //imageId = AppData.get().getUserInfo().getImageId();
            //userName = AppData.get().getUserInfo().getNickname();
            userName = "Berwin";
            Glide.with(mContext)
                    .load(R.mipmap.ic_user_head)
                    .into(civAvatar);
            //loadCircleImage(civAvatar,imageId,false);
        }else{
            imageId = chatUserInfo.getTargetUserAvatar();
            userName = chatUserInfo.getTargetNickName();
            //userName = getNameByUser(item.getSenderId());

            loadCircleImage(civAvatar,imageId,true);
        }

        userText.setText(CommonUtils.format(userName));

        //判断是否显示时间标签
        displaySplitTime(dateText,position);


        final ImageView imageView = helper.getView(R.id.chat_item_content_image);
        TextView contentText = helper.getView(R.id.chat_item_content_text);

        imageView.setVisibility(View.GONE);
        contentText.setVisibility(View.GONE);

        MsgType msgType = MsgType.getMsgType(item.getType());
        if(msgType == null)
            return;

        switch (msgType){
            case text:
                contentText.setVisibility(View.VISIBLE);
                contentText.setText(item.getData());
                break;
            case image:
                imageView.setVisibility(View.VISIBLE);
                String imageUrl = item.getData();
                /*if(!item.getData().contains("/")){
                    imageUrl = DataConstants.BASE_FILE_URL+item.getData();
                }*/
                //ImageLoaderUtil.get().loadImage(context,imageUrl,imageView);

                Glide.with(mContext).load(imageUrl)
                        .placeholder(R.mipmap.img_default_gray)
                        .error(R.mipmap.img_default_gray)
                        .crossFade(1000).fitCenter().into(imageView);
                break;
            case file:
            case voice:
            case location:
                /*String abbr = JMessageConverter.getContentAbbr(item.getType(),
                        item.getData());
                contentText.setVisibility(View.VISIBLE);
                contentText.setText(abbr);*/
                break;
            case custom:
                break;
        }

        //helper.addOnClickListener(R.id.civ_avatar);

        View failView = helper.getView(R.id.chat_item_fail);
        View progressView = helper.getView(R.id.chat_item_progress);
        progressView.setVisibility(View.GONE);
        failView.setVisibility(View.GONE);

        if(item.getMsgStatus() == null)
            return;

        switch (item.getMsgStatus()) {
            case created:
                break;
            case send_success:
                failView.setVisibility(View.GONE);
                progressView.setVisibility(View.GONE);
                break;
            case receive_success:
                //progressView.setVisibility(View.GONE);
                break;
            case send_fail:
            case receive_fail:
                failView.setVisibility(View.VISIBLE);
                progressView.setVisibility(View.GONE);
                break;
            case send_going:
            case receive_going:
                progressView.setVisibility(View.VISIBLE);
                failView.setVisibility(View.GONE);
                break;
            case send_draft:
                break;
        }
    }

    private void loadCircleImage(final ImageView imageView,String avatar,boolean hasSuffix){
        String url = avatar;
        if(!hasSuffix)
            ;
            //url = Constants.BASE_DOMAIN+avatar;
        Glide.with(mContext)
                .load(url)
                .placeholder(R.mipmap.ic_user_head)
                .error(R.mipmap.ic_user_head)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        imageView.setImageDrawable(resource);
                    }
                });
    }

    /*private String getNameByUser(String id){
        AccountInfo info = accountInfoMap.get(id);
        if(info != null && info.getNickname() != null){
            return info.getNickname();
        }

        return "";
    }*/


    private void displaySplitTime(TextView dateText,int position){
        long intervalTime ;
        try {
            String dateStr = getData().get(position).getCreateDate();
            if(!TextUtils.isEmpty(dateStr)) {

                Date date = dateFormat.parse(dateStr);
                if (position == 0) {
                    intervalTime = System.currentTimeMillis();
                } else {
                    String nextDateStr = getData().get(position - 1).getCreateDate();
                    if(!TextUtils.isEmpty(nextDateStr)) {
                        Date nextDate = dateFormat.parse(nextDateStr);
                        intervalTime = date.getTime() - nextDate.getTime();
                    }else{
                        intervalTime = date.getTime();
                    }
                }
                //2个item之间的间隔在1分钟以上才显示时间标签
                if (intervalTime < DateUtils.ONE_MINUTE) {
                    dateText.setVisibility(View.GONE);
                } else {
                    dateText.setVisibility(View.VISIBLE);
                    dateText.setText(DateUtils.getHHMMDateString(date));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
