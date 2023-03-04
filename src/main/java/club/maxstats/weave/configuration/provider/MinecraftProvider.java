package club.maxstats.weave.configuration.provider;

import club.maxstats.weave.util.Constants;
import club.maxstats.weave.util.DownloadUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.gradle.api.Project;

public class MinecraftProvider {

    private final String     version;
    private       JsonObject versionJson;
    private       String     downloadPath;
    private final Project    project;

    public MinecraftProvider(Project project, String version) {
        this.project = project;
        this.version = version;
    }

    public void provide() {
        JsonObject manifestJson = DownloadUtil.getJsonFromURL("https://launchermeta.mojang.com/mc/game/version_manifest_v2.json");
        if (manifestJson != null) {
            JsonArray versionArray = manifestJson.getAsJsonArray("versions");
            JsonObject versionObject = null;

            for (int i = 0; i < versionArray.size(); i++) {
                JsonObject version = versionArray.get(i).getAsJsonObject();
                if (version.get("id").getAsString().equals(this.version)) {
                    versionObject = version;
                    break;
                }
            }

            if (versionObject != null) {
                JsonObject versionJson = DownloadUtil.getJsonFromURL(versionObject.get("url").getAsString());
                if (versionJson != null) {
                    this.versionJson = versionJson;
                    this.downloadPath = Constants.CACHE_DIR + "/" + this.version;

                    JsonObject downloadsObject = versionJson.getAsJsonObject("downloads");
                    JsonObject clientObject = downloadsObject.getAsJsonObject("client");

                    String clientURL = clientObject.get("url").getAsString();
                    String checksum = clientObject.get("sha1").getAsString();

                    DownloadUtil.downloadAndChecksum(clientURL, checksum, this.downloadPath);

                    new MinecraftLibraryProvider(this).provide();
                }
            }
        }
    }

    /**
     * @return The version of Minecraft.
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * @return The path to the downloaded Minecraft jar.
     */
    public String getDownloadPath() {
        return this.downloadPath;
    }

    /**
     * @return The version as a {@link JsonObject}.
     */
    public JsonObject getVersionJson() {
        return this.versionJson;
    }

    /**
     * @return The {@link Project} instance.
     */
    public Project getProject() {
        return this.project;
    }

}
