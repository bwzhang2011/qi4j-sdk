/*  Copyright 2008 Edward Yakop.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
* implied.
*
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.qi4j.library.swing.visualizer.detailPanel.internal.form.composite;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.qi4j.library.swing.visualizer.model.CompositeDetailDescriptor;
import org.qi4j.spi.composite.CompositeDescriptor;

/**
 * @author edward.yakop@gmail.com
 * @see org.qi4j.library.swing.visualizer.model.CompositeDetailDescriptor
 * @since 0.5
 */
public final class CompositeDescriptorForm
{
    private JComponent compositeSeparator;
    private JTextField compositeClassName;
    private JTextField compositeURI;
    private JTextField compositeVisibility;

    private JPanel compositePanel;

    public final void updateModel( CompositeDetailDescriptor<CompositeDescriptor> aDescriptor )
    {
        String className = null;
        String uri = null;
        String visibility = null;

        if( aDescriptor != null )
        {
            // Basic properties
            CompositeDescriptor descriptor = aDescriptor.descriptor();
            className = descriptor.type().getName();
            uri = descriptor.toURI();
            visibility = descriptor.visibility().toString();
        }

        compositeClassName.setText( className );
        compositeURI.setText( uri );
        compositeVisibility.setText( visibility );
    }

    private void createUIComponents()
    {
        DefaultComponentFactory cmpFactory = DefaultComponentFactory.getInstance();
        // TODO: Localization
        compositeSeparator = cmpFactory.createSeparator( "Composite" );
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$()
    {
        createUIComponents();
        compositePanel = new JPanel();
        compositePanel.setLayout( new FormLayout( "left:4px:noGrow,left:p:noGrow,left:4dlu:noGrow,fill:max(p;75dlu):noGrow,left:4dlu:noGrow,left:d:grow,left:4px:noGrow", "top:4px:noGrow,center:p:noGrow,top:4dlu:noGrow,center:p:noGrow,top:4dlu:noGrow,center:p:noGrow,top:4dlu:noGrow,center:p:noGrow,top:4px:noGrow" ) );
        ( (FormLayout) compositePanel.getLayout() ).setRowGroups( new int[][]{ new int[]{ 1, 9 }, new int[]{ 4, 6, 8 }, new int[]{ 5, 7 } } );
        ( (FormLayout) compositePanel.getLayout() ).setColumnGroups( new int[][]{ new int[]{ 1, 7 }, new int[]{ 3, 5 } } );
        CellConstraints cc = new CellConstraints();
        compositePanel.add( compositeSeparator, cc.xyw( 2, 2, 5 ) );
        final JLabel label1 = new JLabel();
        label1.setText( "Class name" );
        compositePanel.add( label1, cc.xy( 2, 4 ) );
        final JLabel label2 = new JLabel();
        label2.setText( "URI" );
        compositePanel.add( label2, cc.xy( 2, 6 ) );
        final JLabel label3 = new JLabel();
        label3.setText( "Visibility" );
        compositePanel.add( label3, cc.xy( 2, 8 ) );
        compositeClassName = new JTextField();
        compositeClassName.setEditable( false );
        compositePanel.add( compositeClassName, cc.xy( 4, 4 ) );
        compositeURI = new JTextField();
        compositeURI.setEditable( false );
        compositePanel.add( compositeURI, cc.xy( 4, 6 ) );
        compositeVisibility = new JTextField();
        compositeVisibility.setEditable( false );
        compositePanel.add( compositeVisibility, cc.xy( 4, 8 ) );
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$()
    {
        return compositePanel;
    }
}
