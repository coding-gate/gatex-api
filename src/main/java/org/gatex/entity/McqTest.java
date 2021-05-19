package org.gatex.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gatex.model.ValueLabel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="McqTest")
public class McqTest {
    @Id
    String id;
    String title;
    String type;
    String timeLimit;
    List<String> selectedQuestions;
    ValueLabel language;
    String userName;
    boolean isLocked;
    Date createdOn;
 /*
    public static void main(String[] arg) throws JsonProcessingException {

        McqQuestion mcq=new McqQuestion();

        ObjectMapper mapper=new ObjectMapper();

        List<Object> options = new ArrayList<>();
        List<String> val = new ArrayList<>();
        val.add("its same");
        val.add("false");
        options.add(val);
        val = new ArrayList<>();
        val.add("stream is lighter version of stream");
        val.add("true");
        options.add(val);

        mcq.setText("What is the difference between stream and thread");
        mcq.setLang(new ValueLabel("Java","Java"));
        mcq.setComplexity(new ValueLabel("Medium","Medium"));
        mcq.setTime(new ValueLabel("2","2 min"));
        mcq.setTags(new ValueLabel[]{new ValueLabel("array","Array"), new ValueLabel("es6","ES6")});
        mcq.setType("mcq");
        mcq.setOptions(options);
        System.out.println(mapper.writeValueAsString(mcq));
    }
  */

}
