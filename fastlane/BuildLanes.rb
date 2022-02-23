# build (development or staging or production) (release or debug) with values versionCode and versionName
private_lane :build do |options|

  version_code = options[:version_code]
  version_name = options[:version_name]

  puts "Build Version Name: #{version_name}"
  puts "Build Version Code: #{version_code}"

  build_type = options[:build_type]

  task_name = options[:is_bundle] ? 'bundle' : 'assemble'

  puts "Task Name: #{task_name}"

  gradle(
      task: task_name,
      build_type: build_type,
      flavor: ENV["FLAVOR"],
      properties: {
          'versionCode' => version_code,
          'versionName' => version_name
      }
  )
end

desc "Upload (flavor) debug build to Firebase App Distribute"
lane :publishFirebase do |options|

  options[:is_bundle] = false
  preparePublish(options)

  firebase_app_distribution(
      app: ENV["FIREBASE_APP_ID"],
      groups: options[:beta_group],
      apk_path: lane_context[SharedValues::GRADLE_APK_OUTPUT_PATH],
      release_notes: options[:release_notes],
      firebase_cli_token: ENV["FIREBASE_TOKEN"],
      debug: true
  )

end

#upload (flavor) to play store under alpha channel
lane :publish do |options|

  options[:is_bundle] = true
  preparePublish(options)

  upload_to_play_store(
      track: 'internal',
      package_name: ENV["PACKAGE_NAME"],
      json_key: ENV["JSON_KEY"],
      skip_upload_metadata: true,
      skip_upload_images:true,
      skip_upload_screenshots:true,
      timeout: options[:timeout],
      aab: lane_context[SharedValues::GRADLE_AAB_OUTPUT_PATH]
  )
options[:storeName] = "Play store"
  tag(options)
end

# prepare (development or staging or production) (release or debug) with values versionCode and versionName
private_lane :preparePublish do |options|

  # increment_version_code(options)

  puts options[:version_name]
  puts options[:version_code]

  upload_translation(options)
  download_translation(options)

  build(options)

  if options[:beta_group] == "N/A"
    options[:beta_group] = ""
  end

  # prepare_release_notes(options)

  if options[:release_notes] == ""
      options[:release_notes] = ENV["RELEASE_NOTES"]
  end
end

# prepare (release or debug) with values versionCode and versionName
lane :publishHuawei do |options|

  options[:is_bundle] = false
  preparePublish(options)

  huawei_appgallery_connect(
     client_id: ENV["HUAWEI_CLIENT_ID"],
         client_secret: ENV["HUAWEI_CLIENT_SECRET"],
         app_id: ENV["HUAWEI_APP_ID"],
         apk_path: lane_context[SharedValues::GRADLE_APK_OUTPUT_PATH],
         submit_for_review: false
  )
options[:storeName] = "Huawei AppGallery"
  tag(options)
end
