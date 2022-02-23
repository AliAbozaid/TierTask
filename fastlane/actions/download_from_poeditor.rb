require 'net/http'
require 'net/https'
require 'json'
require 'fileutils'

module Fastlane
  module Actions
    class DownloadFromPoeditorAction < Action

      def self.run(params)
        # fastlane will take care of reading in the parameter and fetching the environment variable:
        UI.message "PO Editor API Token: #{params[:api_token]}"
        UI.message "PO Editr project id: #{params[:project_id]}"
        UI.message "Language for exaport: #{params[:language]}"
        UI.message "Export file type: #{params[:type]}"
        UI.message "Export file name: #{params[:file_name]}"
        export(params) 
      end

      def self.export(params) 
        uri = URI('https://api.poeditor.com/v2/projects/export')

        # Create client
        http = Net::HTTP.new(uri.host, uri.port)
        http.use_ssl = true
        http.verify_mode = OpenSSL::SSL::VERIFY_PEER
        data = {
          "api_token" => params[:api_token],
          "id" => params[:project_id],
          "language" => params[:language],
          "type" => params[:type],
        }
        body = URI.encode_www_form(data)

        # Create Request
        req =  Net::HTTP::Post.new(uri)
        req.add_field "Content-Type", "application/x-www-form-urlencoded; charset=utf-8"
        req.body = body

        # Fetch Request
        res = http.request(req)
        puts "Response HTTP Status Code: #{res.code}"
        # puts "Response HTTP Response Body: #{res.body}"

        json = JSON.parse(res.body)
        content = download_file(URI(json["result"]["url"]))
        save(params[:file_name], content)

      rescue StandardError => e
        puts "HTTP Request failed (#{e.message})"
      end

      def self.download_file(uri) 
        # Create client
        http = Net::HTTP.new(uri.host, uri.port)
        http.use_ssl = true
        http.verify_mode = OpenSSL::SSL::VERIFY_PEER

        # Create Request
        req =  Net::HTTP::Get.new(uri)

        # Fetch Request
        res = http.request(req)
        puts "Response HTTP Status Code: #{res.code}"
        # puts "Response HTTP Response Body: #{res.body}"
        return res.body
      rescue StandardError => e
        UI.error("HTTP Request failed (#{e.message})")
      end
      
      def self.save(file_name, content)
        #puts file_name.sub(/strings.xml/)
        dir = file_name[0..file_name.rindex('/')]
        if !(File.exists?(dir)) 
           FileUtils.mkdir_p(dir)
        end
        
        File.open(file_name, 'w') { |file| file.write(content) }
      end

      #####################################################
      # @!group Documentation
      #####################################################
      def self.description
        "Export strings from PO Editor"
      end

      def self.available_options
        [
          FastlaneCore::ConfigItem.new(key: :api_token,
                                       env_name: "FL_DOWNLOAD_FROM_POEDITOR_API_TOKEN",
                                       description: "API Token for DownloadFromPoeditorAction",
                                       verify_block: proc do |value|
                                          UI.user_error!("No API token for DownloadFromPoeditorAction given, pass using `api_token: 'token'`") unless (value and not value.empty?)
                                          # UI.user_error!("Couldn't find file at path '#{value}'") unless File.exist?(value)
                                       end),
          FastlaneCore::ConfigItem.new(key: :project_id,
                                       env_name: "FL_DOWNLOAD_FROM_POEDITOR_PROJECT_ID",
                                       description: "Project id for DownloadFromPoeditorAction",
                                       verify_block: proc do |value|
                                          UI.user_error!("No project id for DownloadFromPoeditorAction given, pass using `project_id: '123'`") unless (value and not value.empty?)
                                       end),
          FastlaneCore::ConfigItem.new(key: :language,
                                       env_name: "FL_DOWNLOAD_FROM_POEDITOR_LANGUAGE",
                                       description: "Language for exporting",
                                       verify_block: proc do |value|
                                          UI.user_error!("No language for DownloadFromPoeditorAction given, pass using `language: 'en'`") unless (value and not value.empty?)
                                       end),
          FastlaneCore::ConfigItem.new(key: :type,
                                       env_name: "FL_DOWNLOAD_FROM_POEDITOR_TYPE",
                                       description: "Type of exported file",
                                       verify_block: proc do |value|
                                          UI.user_error!("No type for DownloadFromPoeditorAction given, pass using `type: 'apple_strings'`") unless (value and not value.empty?)
                                       end),
          FastlaneCore::ConfigItem.new(key: :file_name,
                                       env_name: "FL_DOWNLOAD_FROM_FILE_NAME",
                                       description: "Export to file",
                                       verify_block: proc do |value|
                                          UI.user_error!("No type for DownloadFromPoeditorAction given, pass using `file_name: 'Travel/Resources/Base.lproj/Localizable.strings'`") unless (value and not value.empty?)
                                       end)
        ]
      end

      def self.authors
        ["https://github.com/latyntsev"]
      end

      def self.is_supported?(platform)
        true
      end
    end
  end
end
