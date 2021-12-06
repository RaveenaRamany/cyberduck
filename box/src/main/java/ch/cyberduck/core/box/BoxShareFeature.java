package ch.cyberduck.core.box;

/*
 * Copyright (c) 2002-2021 iterate GmbH. All rights reserved.
 * https://cyberduck.io/
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

import ch.cyberduck.core.AlphanumericRandomStringService;
import ch.cyberduck.core.DescriptiveUrl;
import ch.cyberduck.core.DisabledListProgressListener;
import ch.cyberduck.core.LocaleFactory;
import ch.cyberduck.core.LoginOptions;
import ch.cyberduck.core.PasswordCallback;
import ch.cyberduck.core.Path;
import ch.cyberduck.core.box.io.swagger.client.ApiException;
import ch.cyberduck.core.box.io.swagger.client.api.SharedLinksFilesApi;
import ch.cyberduck.core.box.io.swagger.client.api.SharedLinksFoldersApi;
import ch.cyberduck.core.box.io.swagger.client.model.File;
import ch.cyberduck.core.box.io.swagger.client.model.FilesFileIdaddSharedLinkBody;
import ch.cyberduck.core.box.io.swagger.client.model.FilesfileIdaddSharedLinkSharedLink;
import ch.cyberduck.core.box.io.swagger.client.model.Folder;
import ch.cyberduck.core.box.io.swagger.client.model.FoldersFolderIdaddSharedLinkBody;
import ch.cyberduck.core.box.io.swagger.client.model.FoldersfolderIdaddSharedLinkSharedLink;
import ch.cyberduck.core.exception.BackgroundException;
import ch.cyberduck.core.exception.LoginCanceledException;
import ch.cyberduck.core.features.PromptUrlProvider;
import ch.cyberduck.core.preferences.PreferencesFactory;

import java.net.URI;
import java.text.MessageFormat;

public class BoxShareFeature implements PromptUrlProvider {

    private final BoxSession session;
    private final BoxFileidProvider fileid;

    public BoxShareFeature(final BoxSession session, final BoxFileidProvider fileid) {
        this.session = session;
        this.fileid = fileid;
    }

    @Override
    public boolean isSupported(final Path file, final Type type) {
        return true;
    }

    @Override
    public DescriptiveUrl toDownloadUrl(final Path file, final Object options, final PasswordCallback callback) throws BackgroundException {
        try {
            final String password = this.prompt(file, callback);
            final File link = new SharedLinksFilesApi(new BoxApiClient(session.getClient())).putFilesIdAddSharedLink(
                    "shared_link",
                    fileid.getFileId(file, new DisabledListProgressListener()),
                    new FilesFileIdaddSharedLinkBody().sharedLink(new FilesfileIdaddSharedLinkSharedLink()
                            .password(password)));
            return new DescriptiveUrl(URI.create(link.getSharedLink().getDownloadUrl()), DescriptiveUrl.Type.signed);
        }
        catch(ApiException e) {
            throw new BoxExceptionMappingService(fileid).map(e);
        }
    }

    @Override
    public DescriptiveUrl toUploadUrl(final Path file, final Object options, final PasswordCallback callback) throws BackgroundException {
        try {
            final String password = this.prompt(file, callback);
            final Folder link = new SharedLinksFoldersApi(new BoxApiClient(session.getClient())).putFoldersIdAddSharedLink(
                    "shared_link",
                    fileid.getFileId(file, new DisabledListProgressListener()),
                    new FoldersFolderIdaddSharedLinkBody().sharedLink(new FoldersfolderIdaddSharedLinkSharedLink()
                            .password(password)));
            return new DescriptiveUrl(URI.create(link.getSharedLink().getUrl()), DescriptiveUrl.Type.signed);
        }
        catch(ApiException e) {
            throw new BoxExceptionMappingService(fileid).map(e);
        }
    }

    private String prompt(final Path file, final PasswordCallback callback) {
        String password = null;
        try {
            password = callback.prompt(session.getHost(),
                    LocaleFactory.localizedString("Passphrase", "Cryptomator"),
                    MessageFormat.format(LocaleFactory.localizedString("Create a passphrase required to access {0}", "Credentials"), file.getName()),
                    new LoginOptions().keychain(false).icon(session.getHost().getProtocol().disk())).getPassword();
        }
        catch(LoginCanceledException e) {
            // Ignore no password set
        }
        return password;
    }
}