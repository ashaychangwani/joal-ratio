package org.araymond.joal.core.config;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by raymo on 24/04/2017.
 */
public class AppConfigurationTest {

    @Test
    public void shouldNotBuildIfMinUploadRateIsLessThanZero() {
        assertThatThrownBy(() -> new AppConfiguration(-1L, 190L, 2, "azureus.client", false))
                .isInstanceOf(AppConfigurationIntegrityException.class)
                .hasMessageContaining("minUploadRate must be at least 0.");
    }

    @Test
    public void shouldCreateIfMinUploadRateEqualsZero() {
        final AppConfiguration config = new AppConfiguration(0L, 190L, 2, "azureus.client", false);

        assertThat(config.getMinUploadRate()).isEqualTo(0);
    }

    @Test
    public void shouldNotBuildIfMaxUploadRateIsLessThanOne() {
        assertThatThrownBy(() -> new AppConfiguration(180L, -1L, 2, "azureus.client", false))
                .isInstanceOf(AppConfigurationIntegrityException.class)
                .hasMessageContaining("maxUploadRate must greater than 0.");
    }

    @Test
    public void shouldCreateIfMinUploadRateEqualsOne() {
        final AppConfiguration config = new AppConfiguration(0L, 1L, 2, "azureus.client", false);

        assertThat(config.getMaxUploadRate()).isEqualTo(1);
    }

    @Test
    public void shouldNotBuildIfMaxRateIsLesserThanMinRate() {
        assertThatThrownBy(() -> new AppConfiguration(180L, 150L, 2, "azureus.client", false))
                .isInstanceOf(AppConfigurationIntegrityException.class)
                .hasMessageContaining("maxUploadRate must be strictly greater than minUploadRate.");
    }

    @Test
    public void shouldNotBuildIfMaxRateEqualsThanMinRate() {
        assertThatThrownBy(() -> new AppConfiguration(180L, 180L, 2, "azureus.client", false))
                .isInstanceOf(AppConfigurationIntegrityException.class)
                .hasMessageContaining("maxUploadRate must be strictly greater than minUploadRate.");
    }

    @Test
    public void shouldNotBuildIfSimultaneousSeedIsLessThanOne() {
        assertThatThrownBy(() -> new AppConfiguration(180L, 190L, 0, "azureus.client", false))
                .isInstanceOf(AppConfigurationIntegrityException.class)
                .hasMessageContaining("simultaneousSeed must be greater than 0.");
    }

    @Test
    public void shouldCreateIfSimultaneousSeedIsOne() {
        final AppConfiguration config = new AppConfiguration(180L, 190L, 1, "azureus.client", false);

        assertThat(config.getSimultaneousSeed()).isEqualTo(1);
    }

    @Test
    public void shouldNotBuildIfClientIsNull() {
        assertThatThrownBy(() -> new AppConfiguration(180L, 190L, 2, null, false))
                .isInstanceOf(AppConfigurationIntegrityException.class)
                .hasMessageContaining("client is required, no file name given.");
    }

    @Test
    public void shouldNotBuildIfClientIsEmpty() {
        assertThatThrownBy(() -> new AppConfiguration(180L, 190L, 2, "     ", false))
                .isInstanceOf(AppConfigurationIntegrityException.class)
                .hasMessageContaining("client is required, no file name given.");
    }

    @Test
    public void shouldBuild() {
        final AppConfiguration config = new AppConfiguration(180L, 190L, 2, "azureus.client", false);

        assertThat(config.getMinUploadRate()).isEqualTo(180);
        assertThat(config.getMaxUploadRate()).isEqualTo(190);
        assertThat(config.getSimultaneousSeed()).isEqualTo(2);
        assertThat(config.getClientFileName()).isEqualTo("azureus.client");
    }

    @Test
    public void shouldBeEqualsByProperties() {
        final AppConfiguration config = new AppConfiguration(180L, 190L, 2, "azureus.client", false);
        final AppConfiguration config2 = new AppConfiguration(180L, 190L, 2, "azureus.client", false);
        assertThat(config).isEqualTo(config2);
    }

    @Test
    public void shouldHaveSameHashCodeWithSameProperties() {
        final AppConfiguration config = new AppConfiguration(180L, 190L, 2, "azureus.client", false);
        final AppConfiguration config2 = new AppConfiguration(180L, 190L, 2, "azureus.client", false);
        assertThat(config.hashCode()).isEqualTo(config2.hashCode());
    }

}