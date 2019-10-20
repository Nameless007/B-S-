package com.nameless.service;

import com.nameless.dao.RecordRepository;
import com.nameless.model.Record;
import com.nameless.model.response.Response;
import com.nameless.model.response.ResponseHasInitMsg;
import com.nameless.model.response.ResponseHasRecords;
import com.nameless.model.response.ResultCode;
import com.nameless.model.vo.ContentOfMsgListElement;
import com.nameless.model.vo.FindRecordApiRespone;
import com.nameless.model.vo.MsgListElement;
import com.nameless.model.vo.NewMsgListElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class RecordService {
    @Autowired
    private RecordRepository recordRepository;

    public ResponseHasRecords getRecord(String account) {

        List<Record> all = recordRepository.findAll();


        List<Record> list = recordRepository.findByUserid(account);

        return new ResponseHasRecords(ResultCode.SUCCESS,list);

    }


    @Transactional
    public Record addRecord(Record record) {
        Record res =null;
        try {
            record.setId(null);
            record.setHaveread(0);
            res = recordRepository.save(record);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res;
    }



    //查找留言
    public ResponseHasRecords getRecordByUserAndFriend(String user, String friend) {

        List<Record> list = recordRepository.findByUseridAndFriendidAndHaveread(friend,user,0);

        return new ResponseHasRecords(ResultCode.SUCCESS,list);
    }



    @Transactional
    public void updateStatusHasRead(String id){
        Optional<Record> optional = recordRepository.findById(id);
        if (optional.isPresent()){
            Record record = optional.get();
            record.setHaveread(1);
            recordRepository.save(record);
        }
    }


    public ResponseHasInitMsg init(String account) {
        //打包数据,封装响应对象
        ResponseHasInitMsg res = packageData(account);
        //直接将数据库的消息记录改为已读,暂时先这样，不想写前端了（本来应该由前端确认收到消息后在改成已读）
        setHaveRead(account);
        return res;
    }

    private void setHaveRead(String account) {
        List<Record> list = recordRepository.findByFriendidAndHaveread(account, 0);
        for (Record record : list) {
            record.setHaveread(1);
            recordRepository.save(record);
        }
    }

    private ResponseHasInitMsg packageData(String account) {
        List<Record> res = recordRepository.findByFriendidAndHaveread(account, 0);
        FindRecordApiRespone respone = new FindRecordApiRespone();
        List<MsgListElement> list1 =  createMsgList(res);
        List<NewMsgListElement> list2 = createNewMsgList(list1) ;
        respone.setMsgList(list1);
        respone.setNewMsgList(list2);
        return new ResponseHasInitMsg(ResultCode.SUCCESS,respone);
    }


    private List<NewMsgListElement> createNewMsgList(List<MsgListElement> list1) {
        List<NewMsgListElement> out = new ArrayList<>();
        for (MsgListElement element :list1) {
            NewMsgListElement newMsgListElement = new NewMsgListElement();
            newMsgListElement.setName(element.getName());
            newMsgListElement.setNum(element.getContent().size());
            out.add(newMsgListElement);
        }
        return out;
    }

    private List<MsgListElement> createMsgList(List<Record> in) {
        List<MsgListElement> out = new ArrayList<>();
        HashMap<String, List<String>> map = new HashMap<>();
        for (int i = 0; i < in.size(); i++) {
            Record record = in.get(i);
            if (map.containsKey( record.getUserid() )){
                map.get(record.getUserid()).add(  record.getMessage() );
            }else {
                 List<String> list = new ArrayList<>();
                 list.add(  record.getMessage() );
                 map.put( record.getUserid(),list   );
            }
        }
        Iterator<Map.Entry<String, List<String>>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, List<String>> next = iterator.next();
            MsgListElement element = new MsgListElement();
            element.setName(next.getKey());
            List<ContentOfMsgListElement> contentOfMsgListElementList =   new ArrayList<>();
            for (String str   : next.getValue()) {
                ContentOfMsgListElement content = new ContentOfMsgListElement();
                content.setFlag("left");
                content.setMsg(str);
                contentOfMsgListElementList.add(content);
            }
            element.setContent(contentOfMsgListElementList);
            out.add(element);
        }
        return out;
    }


}
