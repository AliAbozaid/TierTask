
# clean the project
lane :clean do
  gradle(task: 'clean')
end

#download language files from poeditor to res folder
desc 'Download all translation form POEditor'
lane :download_translation do |options|

    #common module
    download_from_poeditor(
	    api_token: ENV["PO_EDITOR_TOKEN"],
	    project_id: ENV["PO_PROJECT_ID"],
	    language:"en",
	    type:"android_strings",
	    file_name: "./common/src/main/res/values/strings.xml"
    )

end

#upload language files to poeditor from res folders
desc 'upload all translation to POEditor'
lane :upload_translation do |options|
  	#File uploads are limited to one every 30 seconds.
    #common module
    upload_to_poeditor(
        api_key: ENV["PO_EDITOR_TOKEN"],
        project_id: ENV["PO_PROJECT_ID"],
        language:"en",
        language_file_path: "./common/src/main/res/values/strings.xml"
    )
  end
