package eu.acengineering.samples.messagebundle;

import eu.acengineering.samples.messagebundle.model.MessageBundle;
import eu.acengineering.samples.messagebundle.model.MessageBundleKey;
import eu.acengineering.samples.messagebundle.model.Page;
import eu.acengineering.samples.messagebundle.model.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;

@ApplicationScoped
@Named(value = MessageBundleService.BEAN_NAME)
@Transactional
public class MessageBundleServiceImpl implements MessageBundleService, Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageBundleService.class);

    private static final long serialVersionUID = -8947835574807816107L;

    @PersistenceContext(unitName = "mb-unit")
    private transient EntityManager entityManager;

    @Override
    public Page<MessageBundle> findPaginated(final PageRequest somePageRequest) {
        List<MessageBundle> messageBundles = entityManager.createQuery(buildQuery(somePageRequest))
                .setFirstResult(somePageRequest.getStart())
                .setMaxResults(somePageRequest.getPageSize())
                .getResultList();

        List<MessageBundle> result = new ArrayList<>();
        messageBundles.stream().map((messageBundle) -> {
            result.add(messageBundle);
            MessageBundle defaultMessageBundle;
            try {
                defaultMessageBundle = (MessageBundle) entityManager.createQuery("select mb from MessageBundle mb where mb.id.key = :key and mb.id.language = :language")
                        .setParameter("key", messageBundle.getId().getKey())
                        .setParameter("language", somePageRequest.getFilters().get(MessageBundle.LANGUAGE_FIELD))
                        .getSingleResult();
            } catch (NoResultException nre) {
                defaultMessageBundle = null;
            }
            return defaultMessageBundle;
        }).filter((defaultMessageBundle) -> (defaultMessageBundle != null)).forEach((defaultMessageBundle) -> {
            result.add(defaultMessageBundle);
        });

        return new Page<>(result, count(somePageRequest));
    }

    @Override
    public List<String> findAllKey() {
        return (List<String>) entityManager.createQuery("select distinct mb.id.key from MessageBundle mb");
    }

    @Override
    public List<MessageBundle> findByKey(final String someKey) {
        return entityManager.createQuery("select mb from MessageBundle mb where mb.id.key = :key")
                .setParameter("key", someKey)
                .getResultList();
    }

    @Override
    public MessageBundle findByKeyAndLanguage(final String someKey, final String someLanguage) {
        MessageBundle mb;
        try {
            mb = (MessageBundle) entityManager.createQuery("select mb from MessageBundle mb where mb.id.key = :key and mb.id.language = :language")
                    .setParameter("key", someKey)
                    .setParameter("language", someLanguage)
                    .getSingleResult();
        } catch (NoResultException nre) {
            try {
                mb = (MessageBundle) entityManager.createQuery("select mb from MessageBundle mb where mb.id.key = :key and mb.id.language = :language")
                        .setParameter("key", someKey)
                        .setParameter("language", "en")
                        .getSingleResult();
            } catch (NoResultException nre2) {
                mb = new MessageBundle();
                MessageBundleKey id = new MessageBundleKey(someKey, someLanguage);
                mb.setId(id);
                mb.setLabel(someKey);
            }
        }
        return mb;
    }

    @Override
    public MessageBundle saveOrUpdate(final MessageBundle someMessageBundle) {

        MessageBundle mb = null;
        try {
            mb = (MessageBundle) entityManager.createQuery("select mb from MessageBundle mb where mb.id = :id")
                    .setParameter("id", someMessageBundle.getId())
                    .getSingleResult();
        } catch (NoResultException nre) {
            LOGGER.debug("new message bundle");
        }
        if (mb == null) {
            entityManager.persist(someMessageBundle);
            return someMessageBundle;
        } else {
            return entityManager.merge(someMessageBundle);
        }
    }

    @Override
    public void delete(final MessageBundle someMessageBundle) {
        MessageBundle messageBundle = entityManager.find(MessageBundle.class, someMessageBundle.getId());
        if (messageBundle != null) {
            messageBundle = entityManager.merge(someMessageBundle);
            entityManager.remove(messageBundle);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //PRIVATE SECTION
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private String buildQuery(final PageRequest somePageRequest) {
        StringBuilder builder = new StringBuilder(300);
        builder.append("select mb from MessageBundle mb where 1=1 and mb.id.language = 'en'");

        //Add filters
        if (somePageRequest.getFilters().containsKey(MessageBundle.KEY_FIELD)) {
            builder.append(" and mb.id.key like '%").append(somePageRequest.getFilters().get(MessageBundle.KEY_FIELD)).append("%' ");
        }
        if (somePageRequest.getFilters().containsKey(MessageBundle.VALUE_FIELD)) {
            builder.append(" and mb.label like '%").append(somePageRequest.getFilters().get(MessageBundle.VALUE_FIELD)).append("%' ");
        }

        //Add order by directive
        if (MessageBundle.KEY_FIELD.equals(somePageRequest.getSortField())) {
            builder.append(" order by mb.id.key ");
        }

        //Add sort order
        if (somePageRequest.getSortField() != null) {
            builder.append(somePageRequest.getSortOrder());
        }

        return builder.toString();
    }

    private int count(final PageRequest somePageRequest) {
        StringBuilder builder = new StringBuilder(200);
        builder.append("select count(distinct mb.id.key) from MessageBundle mb where 1=1 and mb.id.language = 'en' ");
        //Add filters
        if (somePageRequest.getFilters().containsKey(MessageBundle.KEY_FIELD)) {
            builder.append(" and mb.id.key like '%").append(somePageRequest.getFilters().get(MessageBundle.KEY_FIELD)).append("%' ");
        }
        if (somePageRequest.getFilters().containsKey(MessageBundle.VALUE_FIELD)) {
            builder.append(" and mb.label like '%").append(somePageRequest.getFilters().get(MessageBundle.VALUE_FIELD)).append("%' ");
        }

        return ((Long) entityManager.createQuery(builder.toString()).getSingleResult()).intValue();
    }

}
