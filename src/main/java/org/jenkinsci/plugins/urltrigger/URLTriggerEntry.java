package org.jenkinsci.plugins.urltrigger;

import com.sun.jersey.api.client.ClientResponse;
import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.util.Secret;
import jenkins.model.Jenkins;
import org.jenkinsci.Symbol;
import org.jenkinsci.plugins.urltrigger.content.URLTriggerContentType;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Gregory Boissinot
 */
public class URLTriggerEntry implements Serializable, Describable<URLTriggerEntry> {

    private static final long serialVersionUID = 1L;

    public static final int DEFAULT_STATUS_CODE = ClientResponse.Status.OK.getStatusCode();

    private String url;
    private String username;
    private String password;
    private boolean proxyActivated;
    private boolean checkStatus;
    private int statusCode;
    private int timeout; //in seconds
    private boolean checkETag;
    private boolean checkLastModificationDate;
    private boolean inspectingContent;
    private URLTriggerContentType[] contentTypes;

    private transient String ETag;
    private transient long lastModificationDate;

    @DataBoundConstructor
    public URLTriggerEntry () {}

    public String getUrl() {
        return url;
    }

    @DataBoundSetter
    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    @DataBoundSetter
    public void setUsername(String username) {
        this.username = username;
    }

    @SuppressWarnings("unused")
    public String getRealPassword() {
        if (password == null) {
            return "";
        }

        if (password.length() == 0) {
            return "";
        }

        Secret secret = Secret.fromString(password);
        return Secret.toString(secret);
    }

    @DataBoundSetter
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isProxyActivated() {
        return proxyActivated;
    }

    @DataBoundSetter
    public void setProxyActivated(boolean proxyActivated) {
        this.proxyActivated = proxyActivated;
    }

    public boolean isCheckStatus() {
        return checkStatus;
    }

    public boolean isInspectingContent() {
        return inspectingContent;
    }

    @DataBoundSetter
    public void setCheckStatus(boolean checkStatus) {
        this.checkStatus = checkStatus;
    }

    public int getStatusCode() {
        return statusCode;
    }

    @DataBoundSetter
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isCheckLastModificationDate() {
        return checkLastModificationDate;
    }

    @DataBoundSetter
    public void setCheckLastModificationDate(boolean checkLastModifiedDate) {
        this.checkLastModificationDate = checkLastModifiedDate;
    }

    public long getLastModificationDate() {
        return lastModificationDate;
    }

    @DataBoundSetter
    public void setInspectingContent(boolean inspectingContent) {
        this.inspectingContent = inspectingContent;
    }

    @DataBoundSetter
    public void setLastModificationDate(long lastModificationdDate) {
        this.lastModificationDate = lastModificationdDate;
    }

    public URLTriggerContentType[] getContentTypes() {
        // shallow copy to make immutable
        return Arrays.copyOf(contentTypes, contentTypes.length);
    }

    @DataBoundSetter
    public void setContentTypes(URLTriggerContentType[] contentTypes) {
        // shallow copy instead of setting externally mutable array
        this.contentTypes = Arrays.copyOf(contentTypes, contentTypes.length);
    }

    public boolean isCheckETag() {
        return checkETag;
    }

    @DataBoundSetter
    public void setCheckETag(boolean checkETag) {
        this.checkETag = checkETag;
    }

    public String getETag() {
        return ETag;
    }

    @DataBoundSetter
    public void setETag(String ETag) {
        this.ETag = ETag;
    }

    public int getTimeout() {
        return timeout;
    }

    @DataBoundSetter
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isFtp() {
        return url.startsWith("ftp");
    }

    public boolean isHttp() {
        return url.startsWith("http");
    }

    public boolean isHttps() {
        return url.startsWith("https");
    }

    @Override
    public Descriptor<URLTriggerEntry> getDescriptor() {
        return Jenkins.getActiveInstance().getDescriptorOrDie(getClass());
    }

    @Extension
    @Symbol("urlEntry")
    public static class DescriptorImpl extends Descriptor<URLTriggerEntry> {

        @Override
        public String getDisplayName() {
            return "Url trigger entry";
        }
    }
}
