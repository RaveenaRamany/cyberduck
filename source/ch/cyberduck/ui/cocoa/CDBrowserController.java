package ch.cyberduck.ui.cocoa;

/*
 *  Copyright (c) 2003 David Kocher. All rights reserved.
 *  http://cyberduck.ch/
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  Bug fixes, suggestions and comments should be sent to:
 *  dkocher@cyberduck.ch
 */

import ch.cyberduck.core.*;
import com.apple.cocoa.application.*;
import com.apple.cocoa.foundation.*;
import org.apache.log4j.Logger;

import java.util.*;

/**
* @version $Id$
 */
public class CDBrowserController implements Observer {
    private static Logger log = Logger.getLogger(CDBrowserController.class);
    
    // ----------------------------------------------------------
    // Outlets
    // ----------------------------------------------------------
    
    private NSWindow mainWindow; // IBOutlet
    public void setMainWindow(NSWindow mainWindow) {
		this.mainWindow = mainWindow;
    }
		
    private CDBrowserTableDataSource browserModel;
    private NSTableView browserTable; // IBOutlet
    public void setBrowserTable(NSTableView browserTable) {
		this.browserTable = browserTable;
		this.browserTable.setTarget(this);
//		this.browserTable.tableColumnWithIdentifier("TYPE").setDataCell(new NSImageCell());
		this.browserTable.registerForDraggedTypes(new NSArray(NSPasteboard.FilenamesPboardType));
		this.browserTable.setDataSource(this.browserModel = new CDBrowserTableDataSource());
		this.browserTable.setDoubleAction(new NSSelector("browserTableViewDidClickTableRow", new Class[] {Object.class}));
//		NSMutableArray sortDescriptors = new NSMutableArray();
//		sortDescriptors.addObject(new NSSortDescriptor(
//											   "FILENAME", 
//											   true, 
//											   new NSSelector("sortByFilenames", new Class[] {Object.class})
//											   )
//						  );
//		this.browserTable.setSortDescriptors(sortDescriptors);
		
//		this.browserTable.tableColumnWithIdentifier("FILENAME").setSortDescriptorPrototype(
//																					 new NSSortDescriptor(
//										   "FILENAME", 
//										   true, 
//										   new NSSelector("sortByFilenames", new Class[] {NSTableView.class, Object.class}))
//																					 );
    }
		
	public void browserTableViewDidClickTableRow(Object sender) {
		log.debug("browserTableViewDidClickTableRow");
		if(browserTable.numberOfSelectedRows() > 0) {
			Path p = (Path)browserModel.getEntry(browserTable.selectedRow()); //last row selected 
			if(p.isFile() || browserTable.numberOfSelectedRows() > 1) {
				NSEnumerator enum = browserTable.selectedRowEnumerator();
				List items = new ArrayList();
				Session session = browserModel.workdir().getSession().copy();
				while(enum.hasMoreElements()) {
					items.add(browserModel.getEntry(((Integer)enum.nextElement()).intValue()).copy(session));
				}
				CDTransferController controller = new CDTransferController((Path[])items.toArray(new Path[]{}), Queue.KIND_DOWNLOAD);
				controller.transfer();
			}
			if(p.isDirectory())
				p.list();
		}
    }
	
	
//	public void sortByFilenames(NSTableView table, Object o) {
//		log.debug("sortByFilenames:"+o);
//	}
	
    private CDFavoritesTableDataSource favoritesModel;
    private NSTableView favoritesTable; // IBOutlet
    public void setFavoritesTable(NSTableView favoritesTable) {
		this.favoritesTable = favoritesTable;
		this.favoritesTable.setDataSource(this.favoritesModel = new CDFavoritesTableDataSource());
		this.favoritesTable.setTarget(this);
		this.browserTable.registerForDraggedTypes(new NSArray(NSPasteboard.FilenamesPboardType));
//		this.favoritesTable.tableColumnWithIdentifier("ICON").setDataCell(new NSImageCell());
		this.favoritesTable.tableColumnWithIdentifier("FAVORITE").setDataCell(new CDFavoriteCell());
		this.favoritesTable.setDoubleAction(new NSSelector("favoritesTableViewDidClickTableRow", new Class[] {Object.class}));
    }

	public void favoritesTableViewDidClickTableRow(Object sender) {
		log.debug("favoritesTableViewDidClickTableRow");
		if(favoritesTable.clickedRow() != -1) { //table header clicked
			Host host = (Host)CDFavoritesImpl.instance().values().toArray()[favoritesTable.clickedRow()];
			this.mount(host);
		}
    }
	
    private NSComboBox quickConnectPopup; // IBOutlet
    public void setQuickConnectPopup(NSComboBox quickConnectPopup) {
		this.quickConnectPopup = quickConnectPopup;
		this.quickConnectPopup.setTarget(this);
		this.quickConnectPopup.setAction(new NSSelector("quickConnectSelectionChanged", new Class[] {Object.class}));
		this.quickConnectPopup.setUsesDataSource(true);
		this.quickConnectPopup.setDataSource(CDHistoryImpl.instance());
    }
	
	//	private class QuickConnectDatasource implements NSComboBox.DataSource {
 //		private History history = CDHistoryImpl.instance();
 //		private Favorites favorites = CDFavoritesImpl.instance();
 //		
 //		public int numberOfItemsInComboBox(NSComboBox combo) {
 //			return history.values().size() + favorites.values().size();
 //		}
 //		
 //		public Object comboBoxObjectValueForItemAtIndex(NSComboBox combo, int row) {
 //			return null;
 //		}		
 //	}
    
    private NSButton showFavoriteButton; // IBOutlet
    public void setShowFavoriteButton(NSButton showFavoriteButton) {
		this.showFavoriteButton = showFavoriteButton;
		this.showFavoriteButton.setImage(NSImage.imageNamed("drawer.tiff"));
		this.showFavoriteButton.setAlternateImage(NSImage.imageNamed("drawerPressed.tiff"));
		this.showFavoriteButton.setTarget(this);
		this.showFavoriteButton.setAction(new NSSelector("toggleFavoritesDrawer", new Class[] {Object.class}));
    }

    private NSButton editFavoriteButton; // IBOutlet
    public void setEditFavoriteButton(NSButton editFavoriteButton) {
		this.editFavoriteButton = editFavoriteButton;
		this.editFavoriteButton.setImage(NSImage.imageNamed("edit.tiff"));
		this.editFavoriteButton.setAlternateImage(NSImage.imageNamed("editPressed.tiff"));
		this.editFavoriteButton.setTarget(this);
		this.editFavoriteButton.setAction(new NSSelector("editFavoriteButtonClicked", new Class[] {Object.class}));
    }
	
    private NSButton addFavoriteButton; // IBOutlet
    public void setAddFavoriteButton(NSButton addFavoriteButton) {
		this.addFavoriteButton = addFavoriteButton;
		this.addFavoriteButton.setImage(NSImage.imageNamed("add.tiff"));
		this.addFavoriteButton.setAlternateImage(NSImage.imageNamed("addPressed.tiff"));
		this.addFavoriteButton.setTarget(this);
		this.addFavoriteButton.setAction(new NSSelector("addFavoriteButtonClicked", new Class[] {Object.class}));
    }
	
    private NSButton removeFavoriteButton; // IBOutlet
    public void setRemoveFavoriteButton(NSButton removeFavoriteButton) {
		this.removeFavoriteButton = removeFavoriteButton;
		this.removeFavoriteButton.setImage(NSImage.imageNamed("remove.tiff"));
		this.removeFavoriteButton.setAlternateImage(NSImage.imageNamed("removePressed.tiff"));
		this.removeFavoriteButton.setTarget(this);
		this.removeFavoriteButton.setAction(new NSSelector("removeFavoriteButtonClicked", new Class[] {Object.class}));
    }
    
    private NSButton upButton; // IBOutlet
    public void setUpButton(NSButton upButton) {
		this.upButton = upButton;
		this.upButton.setImage(NSImage.imageNamed("up.tiff"));
		this.upButton.setTarget(this);
		this.upButton.setAction(new NSSelector("upButtonClicked", new Class[] {Object.class}));
    }
	
    private NSButton backButton; // IBOutlet
    public void setBackButton(NSButton backButton) {
		this.backButton = backButton;
		this.backButton.setImage(NSImage.imageNamed("back.tiff"));
		this.backButton.setTarget(this);
		this.backButton.setAction(new NSSelector("backButtonClicked", new Class[] {Object.class}));
    }
	
    private NSPopUpButton pathPopup; // IBOutlet
    public void setPathPopup(NSPopUpButton pathPopup) {
		this.pathPopup = pathPopup;
    }
	
    private NSDrawer logDrawer; // IBOutlet
    public void setLogDrawer(NSDrawer drawer) {
		this.logDrawer = drawer;
    }
	
    private NSDrawer favoritesDrawer; // IBOutlet
    public void setFavoritesDrawer(NSDrawer drawer) {
		this.favoritesDrawer = drawer;
    }
    
    private NSProgressIndicator progressIndicator; // IBOutlet
    public void setProgressIndicator(NSProgressIndicator progressIndicator) {
		this.progressIndicator = progressIndicator;
		this.progressIndicator.setIndeterminate(true);
		this.progressIndicator.setUsesThreadedAnimation(true);
    }
	
    private NSTextField statusLabel; // IBOutlet
    public void setStatusLabel(NSTextField statusLabel) {
		this.statusLabel = statusLabel;
		statusLabel.setAttributedStringValue(new NSAttributedString((NSBundle.localizedString("Idle"))));
    }
	
    /**
		* Keep references of controller objects because otherweise they get garbage collected
     * if not referenced here.
     */
    private static NSMutableArray allDocuments = new NSMutableArray();
    
    private CDPathController pathController;
    
    private NSToolbar toolbar;
	
    /**
		* The host this browser window is associated with
     */
    private Host host;
	
    // ----------------------------------------------------------
    // Constructor
    // ----------------------------------------------------------
    
    public CDBrowserController() {
		allDocuments.addObject(this);
		log.debug("CDBrowserController");
        if (false == NSApplication.loadNibNamed("Browser", this)) {
            log.fatal("Couldn't load Browser.nib");
            return;
        }
    }
	
    public void awakeFromNib() {
		log.debug("awakeFromNib");
		NSPoint origin = this.window().frame().origin();
		this.window().setTitle("Cyberduck "+NSBundle.bundleForClass(this.getClass()).objectForInfoDictionaryKey("CFBundleVersion"));
		this.window().setFrameOrigin(new NSPoint(origin.x() + 16, origin.y() - 16));
		this.pathController = new CDPathController(pathPopup);
		// Toolbar
		this.toolbar = new NSToolbar("Cyberduck Toolbar");
		this.toolbar.setDelegate(this);
//		this.toolbar.setDelegate(new CDToolbarDelegate());
		this.toolbar.setAllowsUserCustomization(true);
		this.toolbar.setAutosavesConfiguration(true);
		this.window().setToolbar(toolbar);
		this.window().makeFirstResponder(quickConnectPopup);
    }
		
    // ----------------------------------------------------------
    // Toolbar Delegate
    // ----------------------------------------------------------
    
	public NSToolbarItem toolbarItemForItemIdentifier(NSToolbar toolbar, String itemIdentifier, boolean flag) {
		
		NSToolbarItem item = new NSToolbarItem(itemIdentifier);
		
		if (itemIdentifier.equals(NSBundle.localizedString("New Connection"))) {
			item.setLabel(NSBundle.localizedString("New Connection"));
			item.setPaletteLabel(NSBundle.localizedString("New Connection"));
			item.setToolTip(NSBundle.localizedString("Connect to remote host"));
			item.setImage(NSImage.imageNamed("connect.tiff"));
			item.setTarget(this);
			item.setAction(new NSSelector("connectButtonClicked", new Class[] {Object.class}));
		}
		else if (itemIdentifier.equals(NSBundle.localizedString("Favorites"))) {
			item.setView(showFavoriteButton);
			item.setMinSize(showFavoriteButton.frame().size());
			item.setMaxSize(showFavoriteButton.frame().size());
		}
		else if (itemIdentifier.equals(NSBundle.localizedString("Quick Connect"))) {
			item.setLabel(NSBundle.localizedString("Quick Connect"));
			item.setPaletteLabel(NSBundle.localizedString("Quick Connect"));
			item.setToolTip(NSBundle.localizedString("Connect to host"));
			item.setView(quickConnectPopup);
			item.setMinSize(quickConnectPopup.frame().size());
			item.setMaxSize(quickConnectPopup.frame().size());
		}
		else if (itemIdentifier.equals(NSBundle.localizedString("Refresh"))) {
			item.setLabel(NSBundle.localizedString("Refresh"));
			item.setPaletteLabel(NSBundle.localizedString("Refresh"));
			item.setToolTip(NSBundle.localizedString("Refresh directory listing"));
			item.setImage(NSImage.imageNamed("refresh.tiff"));
			item.setTarget(this);
			item.setAction(new NSSelector("refreshButtonClicked", new Class[] {Object.class}));
		}
		else if (itemIdentifier.equals(NSBundle.localizedString("Download"))) {
			item.setLabel(NSBundle.localizedString("Download"));
			item.setPaletteLabel(NSBundle.localizedString("Download"));
			item.setToolTip(NSBundle.localizedString("Download file"));
			item.setImage(NSImage.imageNamed("download.tiff"));
			item.setTarget(this);
			item.setAction(new NSSelector("downloadButtonClicked", new Class[] {Object.class}));
		}
		else if (itemIdentifier.equals(NSBundle.localizedString("Upload"))) {
			item.setLabel(NSBundle.localizedString("Upload"));
			item.setPaletteLabel(NSBundle.localizedString("Upload"));
			item.setToolTip(NSBundle.localizedString("Upload local file to the remote host"));
			item.setImage(NSImage.imageNamed("upload.tiff"));
			item.setTarget(this);
			item.setAction(new NSSelector("uploadButtonClicked", new Class[] {Object.class}));
		}
		else if (itemIdentifier.equals(NSBundle.localizedString("Get Info"))) {
			item.setLabel(NSBundle.localizedString("Get Info"));
			item.setPaletteLabel(NSBundle.localizedString("Get Info"));
			item.setToolTip(NSBundle.localizedString("Show file attributes"));
			item.setImage(NSImage.imageNamed("info.tiff"));
			item.setTarget(this);
			item.setAction(new NSSelector("infoButtonClicked", new Class[] {Object.class}));
		}
		else if (itemIdentifier.equals(NSBundle.localizedString("Delete"))) {
			item.setLabel(NSBundle.localizedString("Delete"));
			item.setPaletteLabel(NSBundle.localizedString("Delete"));
			item.setToolTip(NSBundle.localizedString("Delete file"));
			item.setImage(NSImage.imageNamed("delete.tiff"));
			item.setTarget(this);
			item.setAction(new NSSelector("deleteButtonClicked", new Class[] {Object.class}));
		}
		else if (itemIdentifier.equals(NSBundle.localizedString("New Folder"))) {
			item.setLabel(NSBundle.localizedString("New Folder"));
			item.setPaletteLabel(NSBundle.localizedString("New Folder"));
			item.setToolTip(NSBundle.localizedString("Create New Folder"));
			item.setImage(NSImage.imageNamed("newfolder.icns"));
			item.setTarget(this);
			item.setAction(new NSSelector("folderButtonClicked", new Class[] {Object.class}));
		}
		else if (itemIdentifier.equals(NSBundle.localizedString("Disconnect"))) {
			item.setLabel(NSBundle.localizedString("Disconnect"));
			item.setPaletteLabel(NSBundle.localizedString("Disconnect"));
			item.setToolTip(NSBundle.localizedString("Disconnect"));
			item.setImage(NSImage.imageNamed("disconnect.tiff"));
			item.setTarget(this);
			item.setAction(new NSSelector("disconnectButtonClicked", new Class[] {Object.class}));
		}
		else {
			// itemIdent refered to a toolbar item that is not provide or supported by us or cocoa.
   // Returning null will inform the toolbar this kind of item is not supported.
			item = null;
		}
		return item;
	}
	
	
	public NSArray toolbarDefaultItemIdentifiers(NSToolbar toolbar) {
		return new NSArray(new Object[] {
			NSBundle.localizedString("New Connection"), 
			NSToolbarItem.SeparatorItemIdentifier, 
			NSBundle.localizedString("Favorites"), 
			NSBundle.localizedString("Quick Connect"), 
			NSBundle.localizedString("Refresh"), 
			NSBundle.localizedString("Get Info"), 
			NSToolbarItem.FlexibleSpaceItemIdentifier, 
			NSBundle.localizedString("Download"), 
			NSBundle.localizedString("Upload"), 
			NSBundle.localizedString("Disconnect")
		});
	}
	
	public NSArray toolbarAllowedItemIdentifiers(NSToolbar toolbar) {
		return new NSArray(new Object[] {
			NSBundle.localizedString("New Connection"), 
			NSBundle.localizedString("Favorites"), 
			NSBundle.localizedString("Quick Connect"), 
			NSBundle.localizedString("Refresh"), 
			NSBundle.localizedString("Download"), 
			NSBundle.localizedString("Upload"), 
			NSBundle.localizedString("Delete"), 
			NSBundle.localizedString("New Folder"), 
			NSBundle.localizedString("Get Info"), 
			NSBundle.localizedString("Disconnect"), 
			NSToolbarItem.CustomizeToolbarItemIdentifier, 
			NSToolbarItem.SpaceItemIdentifier, 
			NSToolbarItem.SeparatorItemIdentifier, 
			NSToolbarItem.FlexibleSpaceItemIdentifier
		});
	}
	
    public NSWindow window() {
		return this.mainWindow;
    }
	
    public void update(Observable o, Object arg) {
		log.debug("update:"+o+","+arg);
		if(o instanceof Session) {
			if(arg instanceof Path) {
				browserModel.setWorkdir((Path)arg);
				java.util.List cache = ((Path)arg).cache();
				java.util.Iterator i = cache.iterator();
				//		log.debug("List size:"+cache.size());
				browserModel.clear();
				while(i.hasNext()) {
					browserModel.addEntry((Path)i.next());
				}
				browserTable.reloadData();
			}
			if(arg instanceof Message) {
				Message msg = (Message)arg;
				if(msg.getTitle().equals(Message.ERROR)) {
					NSAlertPanel.beginCriticalAlertSheet(
										  NSBundle.localizedString("Error"), //title
										  NSBundle.localizedString("OK"),// defaultbutton
										  null,//alternative button
										  null,//other button
										  mainWindow, //docWindow
										  null, //modalDelegate
										  null, //didEndSelector
										  null, // dismiss selector
										  null, // context
										  (String)msg.getContent() // message
										  );
					progressIndicator.stopAnimation(this);
					statusLabel.setAttributedStringValue(new NSAttributedString((String)msg.getContent()));
				}
				// update status label
				else if(msg.getTitle().equals(Message.PROGRESS)) {
					statusLabel.setAttributedStringValue(new NSAttributedString((String)msg.getContent()));
					statusLabel.display();
				}
				else if(msg.getTitle().equals(Message.TRANSCRIPT)) {
					statusLabel.setAttributedStringValue(new NSAttributedString((String)msg.getContent()));
				}
				else if(msg.getTitle().equals(Message.OPEN)) {
					progressIndicator.startAnimation(this);
					browserModel.clear();
					browserTable.reloadData();
					
					mainWindow.setTitle(host.getProtocol()+":"+host.getHostname());
				}
				else if(msg.getTitle().equals(Message.CLOSE)) {
					progressIndicator.stopAnimation(this);
				}
				
				else if(msg.getTitle().equals(Message.START)) {
					progressIndicator.startAnimation(this);
					//@todo disable toolbar
				}
				else if(msg.getTitle().equals(Message.STOP)) {
					progressIndicator.stopAnimation(this);
					statusLabel.setAttributedStringValue(new NSAttributedString((NSBundle.localizedString("Idle"))));
					//@todo enable toolbar
				}
			}
		}
    }
    
	// ----------------------------------------------------------
	// Manage favorites
	// ----------------------------------------------------------
	
    public void editFavoriteButtonClicked(Object sender) {
		int row = favoritesTable.selectedRow();
		if(row != -1) {
			Host item = CDFavoritesImpl.instance().getItem(CDFavoritesImpl.instance().values().toArray()[row].toString());
			CDFavoriteController controller = new CDFavoriteController(item);
			controller.window().makeKeyAndOrderFront(null);
		}
    }
	
    public void addFavoriteButtonClicked(Object sender) {
		if(this.host != null) {
			CDFavoritesImpl.instance().addItem(host);
			this.favoritesTable.reloadData();
		}
    }
	
    public void removeFavoriteButtonClicked(Object sender) {
		int row = favoritesTable.selectedRow();
		if(row != -1) {
			CDFavoritesImpl.instance().removeItem(CDFavoritesImpl.instance().values().toArray()[row].toString());
			this.favoritesTable.reloadData();
		}
    }
	
    // ----------------------------------------------------------
    // Selector methods for the toolbar items
    // ----------------------------------------------------------
    
    public void toggleLogDrawer(Object sender) {
		logDrawer.toggle(this);
    }
	
    public void toggleFavoritesDrawer(Object sender) {
		favoritesDrawer.toggle(this);
    }
	
    public void gotoButtonClicked(Object sender) {
        log.debug("folderButtonClicked");
		CDGotoController controller = new CDGotoController(browserModel.workdir());
		NSApplication.sharedApplication().beginSheet(
											   controller.window(),//sheet
											   mainWindow, //docwindow
											   controller, //modal delegate
											   new NSSelector(
							 "gotoSheetDidEnd",
							 new Class[] { NSPanel.class, int.class, Object.class }
							 ),// did end selector
											   browserModel.workdir()); //contextInfo
    }
    
    public void folderButtonClicked(Object sender) {
        log.debug("folderButtonClicked");
		CDFolderController controller = new CDFolderController();
		NSApplication.sharedApplication().beginSheet(
											   controller.window(),//sheet
											   mainWindow, //docwindow
											   controller, //modal delegate
											   new NSSelector(
							 "newfolderSheetDidEnd",
							 new Class[] { NSPanel.class, int.class, Object.class }
							 ),// did end selector
											   browserModel.workdir()); //contextInfo
    }
	
	
    public void infoButtonClicked(Object sender) {
		log.debug("infoButtonClicked");
//		CDBrowserTable.CDBrowserTableDataSource browserModel = (CDBrowserTable.CDBrowserTableDataSource)browserTable.dataSource();
		Path path = (Path)browserModel.getEntry(browserTable.selectedRow());
		CDInfoController controller = new CDInfoController(path);
		controller.window().makeKeyAndOrderFront(null);
    }
	
    public void deleteButtonClicked(Object sender) {
		log.debug("deleteButtonClicked");
		NSEnumerator enum = browserTable.selectedRowEnumerator();
		Vector files = new Vector();
		StringBuffer alertText = new StringBuffer(NSBundle.localizedString("Really delete the following files? This cannot be undone."));
//		CDBrowserTable.CDBrowserTableDataSource browserModel = (CDBrowserTable.CDBrowserTableDataSource)browserTable.dataSource();
		while(enum.hasMoreElements()) {
			int selected = ((Integer)enum.nextElement()).intValue();
			Path p = (Path)browserModel.getEntry(selected);
			files.add(p);
			alertText.append("\n- "+p.getName());
		}
		NSAlertPanel.beginCriticalAlertSheet(
									   NSBundle.localizedString("Delete"), //title
									   NSBundle.localizedString("Delete"),// defaultbutton
									   NSBundle.localizedString("Cancel"),//alternative button
									   null,//other button
									   this.window(),//window
									   this, //delegate
									   new NSSelector
									   (
			 "deleteSheetDidEnd",
			 new Class[]
			 {
				 NSWindow.class, int.class, Object.class
			 }
			 ),// end selector
									   null, // dismiss selector
									   files, // contextInfo
									   alertText.toString()
									   //					  "Really delete the file '"+path.getName()+"'? This cannot be undone." // message
									   );
    }
    
    public void deleteSheetDidEnd(NSWindow sheet, int returnCode, Object contextInfo) {
		log.debug("deleteSheetDidEnd");
		sheet.orderOut(null);
		switch(returnCode) {
			case(NSAlertPanel.DefaultReturn):
				Vector files = (Vector)contextInfo;
				if(files.size() > 0) {
					Iterator i = files.iterator();
					Path p = null;
					while(i.hasNext()) {
						p = (Path)i.next();
						p.delete();
					}
					p.getParent().list();
				}
					break;
			case(NSAlertPanel.AlternateReturn):
				break;
		}
    }
	
    public void refreshButtonClicked(Object sender) {
		log.debug("refreshButtonClicked");
		browserModel.workdir().list();
    }
	
    public void downloadButtonClicked(Object sender) {
		log.debug("downloadButtonClicked");
//		CDBrowserTable.CDBrowserTableDataSource browserModel = (CDBrowserTable.CDBrowserTableDataSource)browserTable.dataSource();
		NSEnumerator enum = browserTable.selectedRowEnumerator();
		Session session = browserModel.workdir().getSession().copy();
		List items = new ArrayList();
		while(enum.hasMoreElements()) {
			items.add(browserModel.getEntry(((Integer)enum.nextElement()).intValue()).copy(session));
		}
		CDTransferController controller = new CDTransferController((Path[])items.toArray(new Path[]{}), Queue.KIND_DOWNLOAD);
		controller.transfer();
    }
    
    public void uploadButtonClicked(Object sender) {
		log.debug("uploadButtonClicked");
		NSOpenPanel panel = new NSOpenPanel();
		panel.setCanChooseDirectories(true);
		panel.setCanChooseFiles(true);
		panel.setAllowsMultipleSelection(true);
		panel.beginSheetForDirectory(System.getProperty("user.home"), null, null, mainWindow, this, new NSSelector("openPanelDidEnd", new Class[]{NSOpenPanel.class, int.class, Object.class}), null);
    }
	
    public void openPanelDidEnd(NSOpenPanel sheet, int returnCode, Object contextInfo) {
		sheet.orderOut(null);
		switch(returnCode) {
			case(NSPanel.OKButton): {
				//		Path parent = (Path)pathController.getItem(0);
				Path parent = browserModel.workdir();
				// selected files on the local filesystem
				NSArray selected = sheet.filenames();
				java.util.Enumeration enumerator = selected.objectEnumerator();
				List items = new ArrayList();
				Session session = parent.getSession().copy();
				while (enumerator.hasMoreElements()) {
					Path item = parent.copy(session);
					item.setPath(parent.getAbsolute(), new java.io.File((String)enumerator.nextElement()));
					items.add(item);
				}
				CDTransferController controller = new CDTransferController((Path[])items.toArray(new Path[]{}), Queue.KIND_UPLOAD);
				controller.transfer();
				break;
			}
			case(NSPanel.CancelButton): {
				break;
			}
		}
    }
	
    public void backButtonClicked(Object sender) {
		log.debug("backButtonClicked");
		host.getSession().getPreviousPath().list();
    }
	
	public void upButtonClicked(Object sender) {
		log.debug("upButtonClicked");
		browserModel.workdir().getParent().list();
	}
    
    public void drawerButtonClicked(Object sender) {
		log.debug("drawerButtonClicked");
		logDrawer.toggle(mainWindow);
    }
	
    public void quickConnectSelectionChanged(Object sender) {
		log.debug("quickConnectSelectionChanged");
		String input = ((NSControl)sender).stringValue();
		Host host = CDHistoryImpl.instance().getItem(input);
		if(null == host) {
			int index;
			if((index = input.indexOf('@')) != -1)
				host = new Host(input.substring(index+1, input.length()), new Login(input.substring(0, index)));
			else
				host = new Host(input, new Login());
		}
		this.mount(host);
    }
	
    public void connectButtonClicked(Object sender) {
		log.debug("connectButtonClicked");
		//todo keep reference?
		CDConnectionController controller = new CDConnectionController(this);
		NSApplication.sharedApplication().beginSheet(
											   controller.window(),//sheet
											   mainWindow, //docwindow
											   controller, //modal delegate
											   new NSSelector(
							 "connectionSheetDidEnd",
							 new Class[] { NSWindow.class, int.class, Object.class }
							 ),// did end selector
											   null); //contextInfo
    }
	
    public void disconnectButtonClicked(Object sender) {
		this.unmount();
    }
	
    private boolean isMounting = false;
    private boolean mounted = false;
	
    public void mount(Host host) {
		log.debug("mount:"+host);
		this.isMounting = true;
		this.window().makeKeyAndOrderFront(null);
		this.unmount();
		this.host = host;
		this.host.getSession().addObserver((Observer)this);
		this.host.getSession().addObserver((Observer)pathController);
		
		if(this.host.getProtocol().equals(Session.SFTP)) {
			try {
				this.host.setHostKeyVerificationController(new CDHostKeyController(this.window()));
			}
			catch(com.sshtools.j2ssh.transport.InvalidHostFileException e) {
				//This exception is thrown whenever an exception occurs open or reading from the host file.
				NSAlertPanel.beginCriticalAlertSheet(
										 NSBundle.localizedString("Error"), //title
										 NSBundle.localizedString("OK"),// defaultbutton
										 null,//alternative button
										 null,//other button
										 this.window(), //docWindow
										 null, //modalDelegate
										 null, //didEndSelector
										 null, // dismiss selector
										 null, // context
										 NSBundle.localizedString("Could not open or read the host file")+": "+e.getMessage() // message
										 );
			}
		}
		
		Login login = this.host.getLogin();
		login.setController(new CDLoginController(this.window(), host.getLogin()));
		//	if(!login.hasReasonableValues()) {
  //            login.getController().loginFailure("Enter your login name and password:");
  //	}
		this.host.getSession().mount();
		CDHistoryImpl.instance().addItem(host);
		this.isMounting = false;
		this.mounted = true;
    }
	
    public boolean isMounted() {
		return this.mounted;
    }
	
    public void unmount() {
		log.debug("unmount");
		if(host != null) {
			this.host.getSession().deleteObserver((Observer)this);
			this.host.getSession().deleteObserver((Observer)pathController);
			this.host.closeSession();
		}
    }
        
	
    // ----------------------------------------------------------
    // Window delegate methods
    // ----------------------------------------------------------
	
    public boolean windowShouldClose(NSWindow sender) {
		if(host != null) {
			if(host.getSession().isConnected()) {
				NSAlertPanel.beginCriticalAlertSheet(
										 NSBundle.localizedString("Disconnect from"+" "+host.getHostname()), //title
										 NSBundle.localizedString("Disconnect"),// defaultbutton
										 NSBundle.localizedString("Cancel"),//alternative button
										 null,//other button
										 sender,//window
										 this, //delegate
										 new NSSelector
										 (
			"closeSheetDidEnd",
			new Class[]
			{
				NSWindow.class, int.class, Object.class
			}
			),// end selector
										 null, // dismiss selector
										 null, // context
										 NSBundle.localizedString("The connection will be closed.") // message
										 );
				return false;
			}
		}
		return true;
    }
	
    public void windowWillClose(NSNotification notification) {
		this.window().setDelegate(null);
		allDocuments.removeObject(this);
    }
	
    
    // ----------------------------------------------------------
    // IB action methods
    // ----------------------------------------------------------
	
    public void closeSheetDidEnd(NSWindow sheet, int returncode, Object contextInfo) {
		// if multi window app only close the one window with main.close()
		sheet.orderOut(null);
		if(returncode == NSAlertPanel.DefaultReturn) {
			this.unmount();
			this.window().close();
		}
    }
	
    public boolean validateToolbarItem(NSToolbarItem item) {
		//	log.debug("validateToolbarItem:"+item.label());
		String label = item.label();
		backButton.setEnabled(pathController.numberOfItems() > 0);
		upButton.setEnabled(pathController.numberOfItems() > 0);
		pathPopup.setEnabled(pathController.numberOfItems() > 0);
		if(label.equals(NSBundle.localizedString("New Connection"))) {
			return !this.isMounting;
		}
		if(label.equals(NSBundle.localizedString("Refresh"))) {
			return this.isMounted();
		}
		else if(label.equals(NSBundle.localizedString("Download"))) {
			return this.isMounted() && browserTable.selectedRow() != -1;
		}
		else if(label.equals(NSBundle.localizedString("Upload"))) {
			return this.isMounted();
		}
		else if(label.equals(NSBundle.localizedString("Delete"))) {
			return this.isMounted() && browserTable.selectedRow() != -1;
		}
		else if(label.equals(NSBundle.localizedString("New Folder"))) {
			return this.isMounted();
		}
		else if(label.equals(NSBundle.localizedString("Get Info"))) {
			return this.isMounted() && browserTable.selectedRow() != -1;
		}
		else if (label.equals(NSBundle.localizedString("Disconnect"))) {
			return this.isMounted() && host.getSession().isConnected();
		}
		return true;
    }
    
    public boolean validateMenuItem(_NSObsoleteMenuItemProtocol cell) {
		//	log.debug("validateMenuItem:"+aCell);
        String sel = cell.action().name();
		log.debug("validateMenuItem:"+sel);
        if (sel.equals("gotoButtonClicked:")) {
			return this.isMounted();
        }
        if (sel.equals("infoButtonClicked:")) {
			return this.isMounted() && browserTable.selectedRow() != -1;
        }
        if (sel.equals("folderButtonClicked:")) {
			return this.isMounted();
        }
        if (sel.equals("deleteButtonClicked:")) {
			return this.isMounted() && browserTable.selectedRow() != -1;
        }
        if (sel.equals("refreshButtonClicked:")) {
			return this.isMounted();
        }
        return true;
    }
}