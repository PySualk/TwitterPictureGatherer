package twitterpicturegatherer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@AutoConfigureAfter(DispatcherServletAutoConfiguration.class)
public class CustomWebMvcAutoConfig extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter {

	@Value("${picture.directory}")
	private String picturePath;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		if (!picturePath.endsWith("/"))
			picturePath += "/";
		String myExternalFilePath = "file:////" + picturePath;
		registry.addResourceHandler("/pictures/**").addResourceLocations(myExternalFilePath);
		super.addResourceHandlers(registry);
	}

}
