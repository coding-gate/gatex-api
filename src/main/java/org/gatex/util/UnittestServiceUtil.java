package org.gatex.util;

import lombok.extern.slf4j.Slf4j;
import org.gatex.model.CmdOutput;
import org.gatex.model.UnitTestAndAnswerDTO;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class UnittestServiceUtil {

    public static ResponseEntity<CmdOutput> executeUnittest(Environment env, String language, UnitTestAndAnswerDTO testAns){

        final String HOST=env.getProperty("unittest.api.host");
        final String PORT=env.getProperty("unittest.api.port");

        StringBuilder sb = new StringBuilder("http://");
        sb.append(HOST);
        sb.append(":");
        sb.append(PORT);
        sb.append("/");
        sb.append(language);
        sb.append("/unittest");

        log.debug("Calling URL {}", sb.toString());

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.postForEntity(sb.toString(), testAns, CmdOutput.class);
    }
}
