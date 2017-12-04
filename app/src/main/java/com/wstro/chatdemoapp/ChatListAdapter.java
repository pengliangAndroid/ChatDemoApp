package com.wstro.chatdemoapp;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wstro.app.common.utils.DateUtils;
import com.wstro.chatdemoapp.model.ChatRecordInfo;
import com.wstro.chatdemoapp.model.ChatSession;
import com.wstro.chatdemoapp.model.ChatType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ChatListAdapter extends BaseQuickAdapter<ChatSession, BaseViewHolder> {
    private SimpleDateFormat dateFormat;

    public ChatListAdapter(int layoutResId, List<ChatSession> data) {
        super(layoutResId, data);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final ChatSession item) {
        convertChatMsg(baseViewHolder, item);
    }

    private void convertChatMsg(BaseViewHolder helper, ChatSession item) {
        ChatRecordInfo msg = item.getLastMessage();
        if (msg != null) {
            String date = msg.getCreateDate();
            if(!TextUtils.isEmpty(date)) {
                helper.setText(R.id.tv_time, DateUtils
                        .getDateString(parseDate(date)));
            }
            /*String abbr = JMessageConverter.getContentAbbr(msg.getType(),
                    msg.getData());*/
            helper.setText(R.id.tv_detail, msg.getData());
        }else{
            helper.setText(R.id.tv_detail,"[暂无消息]");
        }

        final int pos = helper.getLayoutPosition();
        helper.getView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(pos);
            }
        });
        helper.addOnClickListener(R.id.content);

        /*ImageView badgeView = helper.getView(R.id.iv_news_badge);
        setBadgeCount(mContext,badgeView,item.getUnread());*/

        ImageView imageView = helper.getView(R.id.civ_image);

        String chatTypeStr = item.getType();
        ChatType chatType = ChatType.typeOf(chatTypeStr);
        if(chatType == ChatType.CUSTOM_SERVICE){
            ChatSession.ExtraBean extra = item.getExtra();
            if(extra != null){
                if(TextUtils.isEmpty(extra.getKefuName())) {
                    helper.setText(R.id.tv_title,"无昵称");
                }else{
                    helper.setText(R.id.tv_title,extra.getKefuName());
                }

                loadImage(imageView,extra.getKefuLogo(),true);
            }
        }else{
            if(item.getUser() != null) {
                helper.setText(R.id.tv_title, item.getUser().getNickname());
                loadImage(imageView, item.getUser().getAvatar(), false);
            }
        }

        View failView = helper.getView(R.id.chat_item_fail);

        if (msg == null || msg.getMsgStatus() == null)
            return;

        switch (msg.getMsgStatus()) {
            case created:
                break;
            case send_success:
            case receive_success:
                failView.setVisibility(View.GONE);
                break;
            case send_fail:
            case receive_fail:
                failView.setBackgroundResource(R.mipmap.ic_fail_resend);
                failView.setVisibility(View.VISIBLE);
                break;
            case send_going:
            case receive_going:
                failView.setBackgroundResource(R.mipmap.ic_sending);
                failView.setVisibility(View.VISIBLE);
                break;
            case send_draft:
                break;
        }
    }

    public Date parseDate(String date){
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*public void setBadgeCount(Context context,ImageView imageView, int count){
        BadgeView badgeView = (BadgeView) imageView.getTag();
        if(badgeView == null) {
            badgeView = new BadgeView(context);
            badgeView.setBackground(12, Color.RED);
            imageView.setTag(badgeView);
        }

        if(count >= 99){
            badgeView.setText("99+", TextView.BufferType.NORMAL);
        }else{
            badgeView.setBadgeCount(count);
        }

        if (count <= 0) {
            badgeView.setTargetView(null);
        } else {
            //newsBadgeView.setBadgeCount(count);
            badgeView.setTargetView(imageView);
        }
    }*/

    private void loadImage(final ImageView imageView,String avatar,boolean hasSuffix){
        String url = avatar;
        if(!hasSuffix){
            //url = DataConstants.BASE_FILE_URL+avatar;
        }

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

    /*public void resetCount(int sessionId){
        int index = getChatSessionIndex(sessionId);
        if(index != -1) {
            int unread = getItem(index).getUnread();

            getItem(index).setUnread(0);

            JChatManager.get().msgSub(unread);
            notifyDataSetChanged();
        }
    }*/

    private int getChatSessionIndex(int sessionId){
        int index = -1;
        List<ChatSession> data = getData();
        for (int i = 0; i < data.size(); i++) {
            if(data.get(i).getId() == sessionId){
                index = i;
                break;
            }
        }

        return index;
    }

    private void delete(int position){
        remove(position);
    }

}
