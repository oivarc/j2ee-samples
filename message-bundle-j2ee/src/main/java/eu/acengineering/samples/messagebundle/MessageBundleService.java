package eu.acengineering.samples.messagebundle;

import eu.acengineering.samples.messagebundle.model.MessageBundle;
import eu.acengineering.samples.messagebundle.model.Page;
import eu.acengineering.samples.messagebundle.model.PageRequest;

import java.util.List;

public interface MessageBundleService {

    String BEAN_NAME = "messageBundleService";

    Page<MessageBundle> findPaginated(PageRequest somePageRequest);

    List<String> findAllKey();

    List<MessageBundle> findByKey(String someKey);

    MessageBundle findByKeyAndLanguage(String someKey, String someLanguage);

    MessageBundle saveOrUpdate(MessageBundle someMessageBundle);

    void delete(MessageBundle someMessageBundle);
}
