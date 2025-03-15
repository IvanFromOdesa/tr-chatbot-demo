package dev.ivank.trchatbotdemo.common.i18n.translate;

import dev.ivank.trchatbotdemo.common.i18n.Language;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Service
@ConditionalOnProperty(name = "translation.provider", havingValue = "libre")
public class LibreTranslationService implements TranslationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LibreTranslationService.class);

    private final RestTemplate libreRestTemplate;

    private static final String TRANSLATE_ENDPOINT = "/translate";
    private static final String DETECT_ENDPOINT = "/detect";

    private static final String REQUEST_QUERY_PARAM = "q";
    private static final String REQUEST_SOURCE_LANG_PARAM = "source";
    private static final String REQUEST_TARGET_LANG_PARAM = "target";

    @Autowired
    public LibreTranslationService(@Qualifier(LibreTranslateConfiguration.REST_TEMPLATE) RestTemplate libreRestTemplate) {
        this.libreRestTemplate = libreRestTemplate;
    }

    @Override
    public String translate(String text, String sourceLang, String targetLang) throws TranslationException {
        Map<String, String> request = new HashMap<>();
        request.put(REQUEST_QUERY_PARAM, text);
        request.put(REQUEST_SOURCE_LANG_PARAM, getLangCode(sourceLang));
        request.put(REQUEST_TARGET_LANG_PARAM, getLangCode(targetLang));

        try {
            ResponseEntity<TranslationResponse> responseEntity = libreRestTemplate.postForEntity(TRANSLATE_ENDPOINT, request, TranslationResponse.class);

            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                LOGGER.error("Translation failed. Response: {}", responseEntity);
                throw new TranslationException("Translation failed.");
            } else {
                responseEntity.getBody();
            }
            TranslationResponse response = responseEntity.getBody();

            if (response.translatedText() == null) {
                LOGGER.error("Translated text is null. Response: {}", response);
                throw new TranslationException("Translated text is null.");
            }

            return response.translatedText();

        } catch (HttpClientErrorException e) {
            LOGGER.error("HTTP error during translation: {}", e.getResponseBodyAsString(), e);
            throw new TranslationException("HTTP error during translation.", e);
        } catch (RestClientException e) {
            LOGGER.error("Error during translation: {}", e.getMessage(), e);
            throw new TranslationException("Error during translation.", e);
        }
    }

    private static String getLangCode(String sourceLang) {
        return sourceLang.equals("en-US") ? "en" : sourceLang;
    }

    @Override
    public Language detectLanguage(String text) throws TranslationException {
        Map<String, String> request = new HashMap<>();
        request.put(REQUEST_QUERY_PARAM, text);

        try {
            ResponseEntity<LanguageDetectionResult[]> responseEntity = libreRestTemplate.postForEntity(DETECT_ENDPOINT, request, LanguageDetectionResult[].class);

            if (responseEntity.getStatusCode() != HttpStatus.OK || responseEntity.getBody().length == 0) {
                LOGGER.error("Language detection failed. Response: {}", responseEntity);
                throw new TranslationException("Language detection failed.");
            }

            LanguageDetectionResult[] response = responseEntity.getBody();
            LanguageDetectionResult result = Arrays
                    .stream(response)
                    .max(Comparator.comparingDouble(l -> l.confidence))
                    .orElseThrow();

            String languageAlias = result.language();

            if (languageAlias == null) {
                LOGGER.error("Language alias is null. Response: {}", result);
                throw new TranslationException("Language alias is null");
            }

            // Libre Translate recognizes norwegian as danish. Or Dutch. Don't ask me why
            return languageAlias.equals("da") || languageAlias.equals("nl") ?
                    Language.BOKMAL : languageAlias.equals("en") ?
                    Language.ENGLISH : Language.byAlias(languageAlias);
        } catch (HttpClientErrorException e) {
            LOGGER.error("HTTP error during language detection: {}", e.getResponseBodyAsString(), e);
            throw new TranslationException("HTTP error during language detection.", e);
        } catch (RestClientException e) {
            LOGGER.error("Error during language detection: {}", e.getMessage(), e);
            throw new TranslationException("Error during language detection.", e);
        }
    }

    public static class TranslationException extends Exception {
        public TranslationException(String message) {
            super(message);
        }

        public TranslationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public record TranslationResponse(String translatedText) {

    }

    public record LanguageDetectionResult(String language, double confidence) {

    }
}
