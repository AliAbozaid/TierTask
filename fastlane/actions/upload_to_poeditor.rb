require 'rest-client'
require 'fileutils'

module Fastlane
  module Actions
    class UploadToPoeditorAction < Action

      def self.run(params)

        if !(File.exists?(params[:language_file_path])) 
            UI.message("Couldn't find file #{params[:language_file_path]}")
           return
        end
        UI.message("Uploading localization file #{params[:language_file_path]} to POEditor")

        response = RestClient.post('https://api.poeditor.com/v2/projects/upload/',
          :api_token => params[:api_key],
          :id => params[:project_id],
          :updating => 'terms_translations',
          :overwrite => 0,
          :sync_terms => 0,
          :language => params[:language],
          :file => File.new(params[:language_file_path], 'rb'),
        )

        UI.message("Finished uploading file")
        UI.message("#{response}")

        return response
      end


      #####################################################
      # @!group Documentation
      #####################################################

      def self.description
        "Upload strings to POEditor"
      end

      def self.authors
        ["Kostia Myts"]
      end

      def self.return_value
        "The content of POEditor API response"
      end

      def self.details
        "Upload strings to POEditor."
      end

      def self.available_options
        [
          FastlaneCore::ConfigItem.new(key: :language_file_path,
                                  env_name: "LANGUAGE_FILE_PATH",
                               description: "Langage file to upload in POEditor",
                                  optional: false,
                                      type: String),
          FastlaneCore::ConfigItem.new(key: :api_key,
                                  env_name: "POEDITOR_API_KEY",
                               description: "API Key for POEditor",
                                  optional: false,
                                      type: String),
          FastlaneCore::ConfigItem.new(key: :project_id,
                                  env_name: "POEDITOR_PROJECT_ID",
                               description: "Project Id in POEditor",
                                  optional: false,
                                      type: String),
          FastlaneCore::ConfigItem.new(key: :language,
                                  env_name: "POEDITOR_LANGUAGE",
                               description: "Exported language",
                                  optional: false,
                                      type: String),

        ]
      end

      def self.is_supported?(platform)
        true
      end
    end
  end
end