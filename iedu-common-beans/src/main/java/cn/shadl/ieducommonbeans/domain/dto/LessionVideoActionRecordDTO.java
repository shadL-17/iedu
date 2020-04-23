package cn.shadl.ieducommonbeans.domain.dto;

import cn.shadl.ieducommonbeans.domain.Lession;
import lombok.Data;

@Data
public class LessionVideoActionRecordDTO {
    Lession lession;//对应课目
    String action;//操作类型
    Integer num;//对应操作记录次数
}
